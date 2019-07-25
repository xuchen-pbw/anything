package com.lynch;

import com.lynch.util.base64.base64__;
import org.json.JSONObject;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class LoraDownTest {
    private final static int PORT = 1782;
    private static final String HOSTNAME = "127.0.0.1";
    private static DatagramSocket mSocket;


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
            // 1.初始化DatagramSocket
            mSocket = new DatagramSocket();
            mSocket = new DatagramSocket();
            // 2.创建用于发送消息的DatagramPacket
            InetSocketAddress address = new InetSocketAddress(HOSTNAME, PORT);
            DatagramPacket sendPacket = new DatagramPacket(phyDown, phyDown.length, address);
            // 3.向服务端发送消息
            mSocket.send(sendPacket);
            // 4.创建用于接收消息的DatagramPacket
            byte[] receiveData1 = new byte[4];
            DatagramPacket receivePacket1 = new DatagramPacket(receiveData1, receiveData1.length);
            // 5.接收服务端消息
            mSocket.receive(receivePacket1);
            System.out.println("ack:" + receiveData1[3]);
            System.out.println("服务端说: ");
            base64__.myprintHex(receiveData1);

            byte[] receiveData2 = new byte[1024];
            DatagramPacket receivePacket2 = new DatagramPacket(receiveData2, receiveData2.length);
            // 5.接收服务端消息
            mSocket.receive(receivePacket2);
            String str = new String(receivePacket2.getData());
            str = str.substring(str.indexOf("{"), str.lastIndexOf("}") + 1);
            System.out.println("ack:" + receiveData2[3]);
            System.out.println("服务端说:" + str);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } finally {
            if (mSocket != null) {
                mSocket.close();
            }
        }


    }
}
