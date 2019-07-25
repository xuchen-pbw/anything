package com.lynch.util;


import java.util.HashMap;

/**
 * Created by lynch on 2018/12/11. <br>
 **/
public class NodeLocation {

    public static HashMap<String, String> getnodeLocation() {
        HashMap<String, String> nodeLocation = new HashMap<>();
        double min = -0.00005;//最小值
        double max = 0.00005;//总和
        int scl = 5;//小数最大位数
        int pow = (int) Math.pow(10, scl);//指定小数位
        double rand = Math.floor((Math.random() * (max - min) + min) * pow) / pow;
        String latitude = String.valueOf(30.75356 + rand);
        String longtude = String.valueOf(103.92813 + rand);

        nodeLocation.put("latitude", latitude);
        nodeLocation.put("longitude", longtude);

        return nodeLocation;

    }


}
