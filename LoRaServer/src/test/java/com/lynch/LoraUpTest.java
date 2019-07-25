package com.lynch;


import com.lynch.util.base64.base64__;
import io.netty.util.CharsetUtil;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * Created by lynch on 2019-03-29. <br>
 **/
public class LoraUpTest {

    private final static int PORT = 1780;
    private static final String HOSTNAME = "127.0.0.1";
    private static DatagramSocket mSocket;

    public static void main(String[] args) {

        String s = "{\"rxpk\":[{\"tmst\":28049631,\"time\":\"2019-03-29T20:12:17.000000Z\",\"tmms\":1237896756000,\"chan\":0,\"rfch\":0,\"freq\":433.375000,\"stat\":1,\"modu\":\"LORA\",\"datr\":\"SF12BW125\",\"codr\":\"4/5\",\"lsnr\":8.2,\"rssi\":-42,\"size\":15,\"data\":\"QJa/3wGA4jkDyKMQFG1Y\"}]}";
        byte[] buf = s.getBytes();
        byte[] phyDown = new byte[buf.length + 12];
        byte[] version = new byte[]{0x02};
        byte[] random = new byte[]{0x01, 0x02};
        byte[] queueMac = new byte[]{0x00};
        byte[] gatewayMac = new byte[]{0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08};
        System.arraycopy(version, 0, phyDown, 0, 1);//version
        System.arraycopy(random, 0, phyDown, 1, 2);
        System.arraycopy(queueMac, 0, phyDown, 3, 1);
        System.arraycopy(gatewayMac, 0, phyDown, 4, 8);
        System.arraycopy(buf, 0, phyDown, 12, buf.length);


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
            byte[] receiveData = new byte[4];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            // 5.接收服务端消息
            mSocket.receive(receivePacket);
            System.out.println("ack:" + receiveData[3]);
            System.out.print("服务端说: ");
            base64__.myprintHex(receiveData);
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
