package com.lynch;

import com.lynch.mac.CalPingoffset;
import com.lynch.util.ByteArrayandInt;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by lynch on 2018/12/28. <br>
 **/
public class BeaconStart {
    public static void main(String[] args) {
        int SecondSinceEpoch1 = (int) ((System.currentTimeMillis() + 18000) / 1000 - 315964800);
        int mode = SecondSinceEpoch1 % 128;
        Timer timer = new Timer();
        timer.schedule(new showbeacon_time(), 128000 - 1000 * mode, 128000);
    }

    public static class showbeacon_time extends TimerTask {
        @Override
        public void run() {
            CalPingoffset calPingoffset = new CalPingoffset();
            calPingoffset.showbeacontime();
            System.out.println(ByteArrayandInt.byteArrayToInt(LoRaServer.beacon_time));
        }
    }

}
