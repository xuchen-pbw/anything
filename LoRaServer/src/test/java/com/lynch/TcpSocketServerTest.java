package com.lynch;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by lynch on 2019-04-08. <br>
 **/
public class TcpSocketServerTest {
    public static void main(String[] args) {
        byte[] phyDown = new byte[12];
        byte[] version = new byte[]{0x02};
        byte[] random = new byte[]{0x01, 0x02};
        byte[] queueMac = new byte[]{0x02};
        byte[] gatewayMac = new byte[]{0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08};
        System.arraycopy(version, 0, phyDown, 0, 1);//version
        System.arraycopy(random, 0, phyDown, 1, 2);
        System.arraycopy(queueMac, 0, phyDown, 3, 1);
        System.arraycopy(gatewayMac, 0, phyDown, 4, 8);
        try {
            // 1. 创建一个服务器端的ServerSocket,指定绑定端口并且监听
            ServerSocket serverSocket = new ServerSocket(1782);
            System.out.println("---服务器即将启动，等待客户端连接---");
            // 2. 调用 accept()方法监听客户端请求
            Socket socket = serverSocket.accept();
            // 3. 利用输入流读取客户端信息
            InputStream is = socket.getInputStream();//字节输入流
            InputStreamReader isr = new InputStreamReader(is);//将字节流转化为字符流
            BufferedReader br = new BufferedReader(isr);//为输入字符流创建缓冲

            String info = null;
            while ((info = br.readLine()) != null) {//循环读取客户端的信息
                System.out.println("我是服务器，客户端说" + info);
            }
            socket.shutdownInput();// 关闭输入流
            // 4.创建输出流，响应客户端的请求
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os);//包装为打印流(与输出字符流类似)
            pw.write("服务器端说，欢迎你！");
            pw.flush();//调用 flush()方法刷新缓存

            // 5.关闭所有资源
            pw.close();
            os.close();
            br.close();
            isr.close();
            is.close();
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
