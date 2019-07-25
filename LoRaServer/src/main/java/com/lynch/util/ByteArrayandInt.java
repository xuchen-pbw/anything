package com.lynch.util;


import com.lynch.util.base64.base64__;

import java.text.DecimalFormat;

/**
 * Created by lynch on 2018/7/2. <br>
 **/
public class ByteArrayandInt {
    public static byte[] intToByteArray(int IntNum) {
        //低位在前，高位在后
        return new byte[]{
                (byte) (IntNum & 0xFF),
                (byte) ((IntNum >> 8) & 0xFF),
                (byte) ((IntNum >> 16) & 0xFF),
                (byte) ((IntNum >> 24) & 0xFF)};
    }
    public static int byteArrayToInt(byte[] bytes) {
        int reInt = 0;
        if (bytes != null) {
            reInt = bytes[0] & 0xFF |
                    (bytes[1] & 0XFF) << 8 |
                    (bytes[2] & 0xFF) << 16 |
                    (bytes[3] & 0xFF) << 24;
        }
        return reInt;
    }

    //整数转16进制
    public static String inttohex(int num) {
        String hex = Integer.toHexString(num);
        return hex;

    }

    //hex to byte
    public static byte hexToByte(String inHex) {
        return (byte) Integer.parseInt(inHex, 16);
    }

    public static int bytetoten(String str) {
        int num = Integer.parseInt(str.replaceAll("^0[x|X]", ""), 16);
        return num;
    }


    public static void main(String[] args) {
        byte bt = 0x46;
        int at = bt;
        System.out.println(at);
        int a = -646;
        byte b[] = null;
        b = intToByteArray(a);
        base64__.myprintHex(b);
        System.out.println(byteArrayToInt(b));
    }

}
