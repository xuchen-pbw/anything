package com.lynch.domain;


import com.lynch.LoRaServer;
import com.lynch.mac.CalPingoffset;
import com.lynch.util.ByteArrayandInt;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lynch on 2018/11/1. <br>
 * 计算class b模式下一个beacon中终端窗口打开的时间起点
 **/
public class ClassBNodeOpens {
    public static List<Long> slotsStart() {
        List<Long> slotstartList = new ArrayList();
        LoRaServer.beacon_time = CalPingoffset.beacontime();
        long millBeacon_time = (long) (ByteArrayandInt.byteArrayToInt(LoRaServer.beacon_time)) * 1000;
//        System.out.println("数据：" + millBeacon_time + "   " + LoRaServer.pingOffset + "   " + LoRaServer.beacon_reserved);
        long finaltime = 0;
        for (int i = 0; i < LoRaServer.pingNb; i++) {
            double time = millBeacon_time + LoRaServer.beacon_reserved + (LoRaServer.pingOffset + i * LoRaServer.ping_period) * LoRaServer.slotLen;
//            double time = LoRaServer.beacon_reserved + (LoRaServer.pingOffset + i * LoRaServer.ping_period) * LoRaServer.slotLen;
            BigDecimal bigDecimal = new BigDecimal(time);
            String strtime = bigDecimal.toPlainString();
            finaltime = Long.parseLong(strtime);
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
//            double cha = (double) 315964800 * 1000;
//            String sd = sdf.format(new Date((long) (finaltime + cha)));   // 时间戳转换成时间
//            System.out.println("slot" + i + "  time:" + sd);
//            System.out.println("slot" + i + " mills:" + finaltime);
            slotstartList.add((finaltime));
        }
        return slotstartList;
    }

}
