package com.lynch.util;

import com.lynch.LoRaServer;
import com.lynch.domain.Device;
import com.lynch.mac.CalPingoffset;
import com.lynch.repository.DeviceRepo;
import com.lynch.util.base64.base64__;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.TimerTask;

/**
 * Created by lynch on 2019/1/7. <br>
 **/
public class BeaconTask extends TimerTask {

    DeviceRepo deviceRepo = (DeviceRepo) SpringTool.getBean("deviceRepo");

    CalPingoffset calPingoffset = new CalPingoffset();

    @Override
    public void run() {
        if (LoRaServer.classMod != ClassMod.Class_B) {
            calPingoffset.showbeacontime();

            List<Device> deviceList = deviceRepo.findAll();
            System.out.println("终端");
            if (deviceList != null) {
                for (Device device : deviceList) {
                    byte[] addr = base64__.HextoByte(device.getDeviceAddr());
                    calPingoffset.calpingoffset(addr);
                }
            }
        } else
            cancel();
    }

}
