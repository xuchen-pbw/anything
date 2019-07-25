package com.lynch;

import com.lynch.util.base64.base64__;

import java.io.*;
import java.net.Socket;

/**
 * Created by lynch on 2019-04-08. <br>
 **/
public class TcpSocketClientTest {
    public static void main(String[] args) {
        String s = "{\"rxpk\":[{\"tmst\":416731004,\"time\":\"2019-04-29T18:22:52.81487000Z\",\"tmms\":1240568591081,\"chan\":4,\"rfch\":0,\"freq\":433.375000,\"stat\":1,\"port\":1,\"modu\":\"LORA\",\"datr\":\"SF12BW125\",\"codr\":\"4/5\",\"lsnr\":8.5,\"rssi\":-15,\"size\":19,\"data\":\"gJq2/wCABAADfhbcrfk0vos/ag==\"}]}";
//        String s = "{\"rxpk\":[{\"tmst\":28049631,\"time\":\"2019-03-29T20:12:17.000000Z\",\"tmms\":1237896756000,\"chan\":0,\"rfch\":0,\"freq\":433.375000,\"stat\":1,\"modu\":\"LORA\",\"datr\":\"SF12BW125\",\"codr\":\"4/5\",\"lsnr\":8.2,\"rssi\":-42,\"size\":15,\"data\":\"QJa/3wGA4jkDyKMQFG1Y\"}]}";
//        byte[] buf = s.getBytes();
//        byte[] phyDown = new byte[buf.length + 12];
        byte[] phyDown = new byte[12];
        byte[] version = new byte[]{0x02};
        byte[] random = new byte[]{0x01, 0x02};
        byte[] queueMac = new byte[]{0x00};
        byte[] gatewayMac = new byte[]{0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08};
        System.arraycopy(version, 0, phyDown, 0, 1);//version
        System.arraycopy(random, 0, phyDown, 1, 2);
        System.arraycopy(queueMac, 0, phyDown, 3, 1);
        System.arraycopy(gatewayMac, 0, phyDown, 4, 8);
//        System.arraycopy(buf, 0, phyDown, 12, buf.length);
        String pyhDownStr = new String(phyDown);
        try {
            // 1.创建一个Socket，指定服务器地址和端口
//            Socket socket = new Socket("47.102.41.135", 1780);
            Socket socket = new Socket("127.0.0.1", 1781);
            // 2.创建一个输出流向服务器发送消息
            OutputStream os = socket.getOutputStream();// 创建输出字节流
            OutputStreamWriter osw = new OutputStreamWriter(os);//转化为字符流
            BufferedWriter bw = new BufferedWriter(osw);//创建缓冲流
            System.out.println(pyhDownStr+s);
            bw.write(pyhDownStr + s);
            bw.flush();
            socket.shutdownOutput();// 关闭输出流
            // 3. 创建输入流，接受服务器响应
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String info = null;
            while ((info = br.readLine()) != null) {
                System.out.println("我是客户端：" + info.getBytes());
                base64__.myprintHex(info.getBytes());
            }
            // 4.关闭所有资源
            br.close();
            isr.close();
            is.close();
            bw.close();
            os.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
