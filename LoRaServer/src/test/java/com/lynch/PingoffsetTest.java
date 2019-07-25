package com.lynch;

import com.lynch.util.ByteArrayandInt;
import org.junit.Test;

/**
 * Created by lynch on 2018/12/19. <br>
 **/
public class PingoffsetTest {
    public static void main(String[] args) {
        final int sum = 60195;
        for (int i = 0; i < 8; i++) {
            int ping_period = (int) Math.pow(2, 5 + i);
            int pingoffset = sum % ping_period;
            System.out.println(i + ": " + ping_period + "    " + pingoffset);
        }
    }

    @Test
    public void testGW() {
        byte[] gwbeacon_time = new byte[]{(byte) 0x80, (byte) 0x08, 0x4f, 0x49};
        byte[] nsbeacon_time = new byte[]{(byte) 0x00, (byte) 0x33, 0x45, 0x49};
        int gw = ByteArrayandInt.byteArrayToInt(gwbeacon_time);
        int ns = ByteArrayandInt.byteArrayToInt(nsbeacon_time);
        int gw_t = 1231122849;
        int ns_t = 1231122908;
        System.out.println(ns_t-gw_t);
//        System.out.println(ns);
//        System.out.println(gw-ns);

    }

}
