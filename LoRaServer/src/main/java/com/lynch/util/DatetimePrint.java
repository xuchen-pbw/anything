package com.lynch.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lynch on 2018/9/27. <br>
 **/
public class DatetimePrint {
    public static String timeprint() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHH:mm:ss:SSS");
        String dateString = formatter.format(currentTime);
        return dateString;
    }
    public static String todateFormat(long num) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        String dateString = formatter.format(num);
        System.out.println(dateString);
        return dateString;
    }
}
