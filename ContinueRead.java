package main;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import com.huawei.iota.iodev.datatrans.DataTransService;
import gnu.io.*;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;

public class ContinueRead extends Thread implements SerialPortEventListener { // SerialPortEventListener
    static CommPortIdentifier portId; // 串口通信管理类
    static Enumeration<?> portList; // 有效连接上的端口的枚举
    InputStream inputStream; // 从串口来的输入流
    OutputStream outputStream;// 向串口输出的流
    static SerialPort serialPort; // 串口的引用

    private static int calls = 0;

    private synchronized void onDataAvailable() {
        byte[] readBuffer = new byte[255];
        try {
            calls ++;
            if (calls != 1) {
                System.out.println("Error! Reenter...");
            }
            int numBytes = -1;
            int len = 0;
            int ID = 0;
            while (inputStream.available() > 0) {
                numBytes = inputStream.read(readBuffer, 0, 1);
                if (numBytes > 0) {
                    if(readBuffer[0] == 0x55) {
                        numBytes = inputStream.read(readBuffer, 1, 1);
                        if(readBuffer[1] == 0x55) {
                            len = inputStream.read();
                            ID = inputStream.read();
                            numBytes = inputStream.read(readBuffer, 0, len - 4);
                            if(numBytes != (len - 4)) {
                                System.out.println("读取数据出错");
                            }else {
                                /* 转换十六进制码流为字符串并上报 */
                                String helperId = String.valueOf(ID>>4);
                                String tagId = ByteToArray(readBuffer,len-4);
                                agentLiteDataTrans.gwDataReport(helperId, tagId);
                                SaveReportInf(String.valueOf(ID>>4)+String.valueOf(ID&0x0F),tagId);
                            }
                        }
                    }
                    readBuffer = new byte[255];// 重新构造缓冲对象，否则有可能会影响接下来接收的数据
                } else {
                    System.out.println("没有读到数据");
                }
            }
        } catch (IOException e) {
            System.out.println(" ============ 串口出错 ============ ");
        } finally {
            calls --;
        }
    }

	@Override
	/*
	* SerialPort EventListene 的方法,持续监听端口上是否有数据流
	*/
    public void serialEvent(SerialPortEvent event) {
        switch (event.getEventType()) {
		case SerialPortEvent.BI:
		case SerialPortEvent.OE:
		case SerialPortEvent.FE:
		case SerialPortEvent.PE:
		case SerialPortEvent.CD:
		case SerialPortEvent.CTS:
		case SerialPortEvent.DSR:
		case SerialPortEvent.RI:
		case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
		    break;
        case SerialPortEvent.DATA_AVAILABLE:// 当有可用数据时读取数据
            onDataAvailable();
            break;
        }
    }

	/**
	* 
	* 通过程序打开COM串口，设置监听器以及相关的参数
	* 
	* @return 返回1 表示端口打开成功，返回 0表示端口打开失败
	*/
    AgentLiteDataTrans agentLiteDataTrans = AgentLiteDataTrans.getInstance();
    DataTransService dataTransService = DataTransService.getInstance();
    public int startComPort() {
		// 通过串口通信管理类获得当前连接上的串口列表
		portList = CommPortIdentifier.getPortIdentifiers();
		dataTransService.registerObserver(agentLiteDataTrans);

		while (portList.hasMoreElements()) {

			// 获取相应串口对象
			portId = (CommPortIdentifier) portList.nextElement();

			// 判断端口类型是否为串口
			if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				// 判断如果串口存在，就打开该串口
				if (portId.getName().equals(AgentLiteBase.COM)) {
					try {
						// 打开串口,延迟为2毫秒
						serialPort = (SerialPort) portId.open(AgentLiteBase.COM, 2000);
						
					} catch (PortInUseException e) {
			            e.printStackTrace();
			            return 0;
			        }
			        // 设置当前串口的输入输出流
			        try {
			            inputStream = serialPort.getInputStream();
			            outputStream = serialPort.getOutputStream();
			        } catch (IOException e) {
			            e.printStackTrace();
			            return 0;
			        }
			        // 给当前串口添加一个监听器
			        try {
			            serialPort.addEventListener(this);
			        } catch (TooManyListenersException e) {
			            e.printStackTrace();
			            return 0;
			        }
					// 设置监听器生效，即：当有数据时通知
					serialPort.notifyOnDataAvailable(true);
			
					// 设置串口的一些读写参数
					try {
						// 比特率、数据位、停止位、奇偶校验位
							serialPort.setSerialPortParams(115200,
							SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
							SerialPort.PARITY_NONE);
					} catch (UnsupportedCommOperationException e) {
							e.printStackTrace();
							return 0;
					}
			
			        return 1;
			    }
            }
        }
        return 0;
    }
	/*
	* byte数组转换十六进制字符串
	*/
	public String ByteToArray(byte[]data,int len) {
	    String result = "";
	    for(int i = 0; i < len && i < data.length; i++) {
	        result+= Integer.toHexString((data[i] & 0xFF) | 0x100).toUpperCase().substring(1, 3);
	    }
	    return result;
	}
	/*
	* 上报信息本地保存
	*/
	public boolean SaveReportInf(String arg0,String arg1) {
		Calendar currentDate = Calendar.getInstance();
		int today = currentDate.get(Calendar.DATE);
		int month = currentDate.get(Calendar.MONTH) + 1;
		int year = currentDate.get(Calendar.YEAR);

        File file = new File("./workdir/report"+year+"年"+month+"月"+today+"日"+".txt");
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
            // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
		/* 获取系统时间 */
		long time = System.currentTimeMillis();
		Date date = new Date(time);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String stringTime = dateFormat.format(date); 
		/* 将上报信息及时间存入本地文档 */
		try {
			FileOutputStream stream = new FileOutputStream(file,true);
			OutputStreamWriter fos = new OutputStreamWriter(stream);
			fos.write(stringTime+" helpID: " + arg0 + " tagID: " + arg1 + "\r\n");
			fos.close();
			return true;
        } catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
        return false;
	}
}
