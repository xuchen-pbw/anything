package com.lynch.app;


import com.lynch.repository.LightRepo;
import com.lynch.service.DeviceService;
import com.lynch.service.LightService;
import com.lynch.util.ByteArrayandInt;
import com.lynch.util.HextoString;
import com.lynch.util.base64.base64__;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * Created by lynch on 2019/3/5. <br>
 * 灯的数据解析
 **/
@Service
public class LightAppService {
    @Autowired
    private LightRepo lightRepo;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private LightService lightService;

    public HashMap<String, String> lightUpHandler(String nodehex, byte[] upData) {
        HashMap<String, String> lightStatus = new HashMap<>();

        String UpData[] = lightDataUp(upData);


        //灯的状态解析
        String nodeSwitch = HextoString.convertHexToString(UpData[5].replaceAll("^0[x|X]", ""));
        String nodeBrightness = String.valueOf(ByteArrayandInt.bytetoten(UpData[4]));
        String nodeThreshold = String.valueOf(ByteArrayandInt.bytetoten(UpData[3]));

        lightStatus.put("nodeSwitch", nodeSwitch);
        lightStatus.put("nodeBrightness", nodeBrightness);
        lightStatus.put("nodeThreshold", nodeThreshold);
        return lightStatus;

    }

    public HashMap<String, String> lightDownHandler(String deviceAddr) {
        HashMap<String, String> lightStatus = new HashMap<>();

        return lightStatus;

    }

    public static String[] lightDataUp(byte[] frmPayload) {
        String Up_Frmpayload = base64__.apptohex(frmPayload);
        String[] updata = Up_Frmpayload.split(" ");
        System.out.print("上行：" + HextoString.convertHexToString(updata[0].replaceAll("^0[x|X]", "")) + " ");
        for (int i = 1; i < 5; i++) {
            System.out.print(ByteArrayandInt.bytetoten(updata[i]) + " ");
        }
        System.out.print(HextoString.convertHexToString(updata[5].replaceAll("^0[x|X]", "")) + " ");
        System.out.println();

        return updata;
    }

    public void lightDataDown(byte[] frmPayload) {
        String Down_Frmpayload = base64__.apptohex(frmPayload);
        String[] downdata = Down_Frmpayload.split(" ");
        System.out.print("下行：" + HextoString.convertHexToString(downdata[0].replaceAll("^0[x|X]", "")) + " ");
        for (int i = 1; i < 5; i++) {
            System.out.print(ByteArrayandInt.bytetoten(downdata[i]) + " ");
        }
        System.out.print(HextoString.convertHexToString(downdata[5].replaceAll("^0[x|X]", "")) + " ");
        System.out.println();
    }

}
