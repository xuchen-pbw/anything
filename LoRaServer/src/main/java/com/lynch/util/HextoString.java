package com.lynch.util;


import com.lynch.util.base64.base64__;

/**
 * Created by lynch on 2018/6/5. <br>
 **/
public class HextoString {

    /**
     * @param hex
     * @return 将hex转换为ascll
     */
    public static String convertHexToString(String hex) {
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < hex.length() - 1; i += 2) {
            String output = hex.substring(i, (i + 2));
            int decimal = Integer.parseInt(output, 16);
            stringBuilder.append((char) decimal);
            temp.append(decimal);

        }
        return stringBuilder.toString();
    }

    /**
     * @param str
     * @return 将ascll转换为hex
     */
    public static String convertStringToHex(String str) {

        char[] chars = str.toCharArray();

        StringBuffer hex = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            hex.append(Integer.toHexString((int) chars[i]));
        }

        return hex.toString();
    }

    public static void main(String[] args) {
        String ascll = "d";
        String hex = convertStringToHex(ascll);
        System.out.println(hex);
        System.out.println(convertHexToString(hex));
        byte[] data = new byte[]{(byte) Integer.parseInt(hex, 16)};
        base64__.myprintHex(data);
        String Down_Frmpayload = base64__.apptohex(data);
        String[] downdata = Down_Frmpayload.split(" ");
        System.out.println(HextoString.convertHexToString(downdata[0].replaceAll("^0[x|X]", "")));
    }
}
