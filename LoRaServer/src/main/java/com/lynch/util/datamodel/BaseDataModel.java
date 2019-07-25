package com.lynch.util.datamodel;

import com.lynch.util.ByteArrayandInt;
import com.lynch.util.HextoString;
import com.lynch.util.base64.base64__;

import java.util.ArrayList;

/**
 * Created by lynch on 2018/12/3. <br>
 **/
public class BaseDataModel {
    /*
     * appType 应用类型
     * appLength 应用数据包长度
     * appData 应用数据
     * */
    private static ArrayList<String> appTypeList = new ArrayList<>();
    private static ArrayList<Integer> appLengthList = new ArrayList<>();
    private static ArrayList<String> appDataList = new ArrayList<>();
    private static int sumLength = 0;


    public static void getappData(byte[] updata) {
        String Up_Frmpayload = base64__.apptohex(updata);
        String[] updata_frm = Up_Frmpayload.split(" ");
        appTypeList.add(updata_frm[sumLength]);
        int appLengthtemp = ByteArrayandInt.bytetoten(updata_frm[sumLength + 1]);
        appLengthList.add(appLengthtemp);
        for (int i = (sumLength + appLengthtemp) - (appLengthtemp - 2); i < sumLength + appLengthtemp; i++)
            appDataList.add(updata_frm[i]);


        for (int j = 0; j < appLengthList.size(); j++) {
            sumLength += appLengthList.get(j);
        }
        if (sumLength < updata_frm.length) {
            getappData(updata);
        } else
            sumLength = 0;
    }

    public static void showappData() {
        int temp = 0;
        for (int i = 0; i < appTypeList.size(); i++) {
            System.out.println("应用" + (i + 1) + "：" + HextoString.convertHexToString(appTypeList.get(i).replaceAll("^0[x|X]", "")));
            System.out.println("应用" + (i + 1) + "数据包长度：" + appLengthList.get(i));
            System.out.print("应用" + (i + 1) + "数据：");
            for (int j = temp; j < temp + appLengthList.get(i) - 2; j++) {
                System.out.print(appDataList.get(j) + " ");
            }
            temp += appLengthList.get(i) - 2;
            System.out.println();

        }
    }

    public static void cleardata() {
        appTypeList.clear();
        appLengthList.clear();
        appDataList.clear();
    }
}
