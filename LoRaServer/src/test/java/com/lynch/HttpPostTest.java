package com.lynch;

import com.lynch.service.DeviceService;
import com.lynch.service.HttpPostService;
import com.lynch.util.ConstantConfig;
import net.sf.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.lynch.util.ConstantConfig.httpPostUrl;

/**
 * Created by lynch on 2019/3/18. <br>
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class HttpPostTest {
    @Autowired
    private HttpPostService httpPostService;
    @Autowired
    private DeviceService deviceService;

    @Test
    public void lightRegister() {
        String nodehex = "9a-b6-ff-00";

        //注册到百亚云
        String result = httpPostService.requestPost("http://i-agent-ns:8080/api", String.valueOf(deviceService.registerDevice(nodehex)));

        System.out.println(result);
    }

    @Test
    public void lightWriteStatusTest() {
        String nodehex = "9a-b6-ff-00";
        String nodeSwitch = "d";
        String nodeBrightness = "110";
        //灯开关
        Integer nodeSwitchInt = null;
        if (nodeSwitch.equals("u"))
            nodeSwitchInt = 1;
        else if (nodeSwitch.equals("d")) {
            nodeSwitchInt = 0;
        }
        String lightSwitch = httpPostService.requestPost(httpPostUrl, String.valueOf(deviceService.writeDeviceInteger("baiya-lora" + nodehex, "0.general_onoff", nodeSwitchInt)));
        System.out.println(lightSwitch);
        //灯亮度
        String lightBrightness = httpPostService.requestPost(httpPostUrl, String.valueOf(deviceService.writeDeviceString("baiya-lora" + nodehex, "0.general_level", nodeBrightness)));
        System.out.println(lightBrightness);
    }

//    @Test
    public void lightReadStatusTest() {
        //从百亚云获取数据
        String lightId = "9a-b6-ff-00";

        //灯开关
        String nodeSwitch = null;
        String resSwitch = httpPostService.requestPost(httpPostUrl, String.valueOf(deviceService.readDevice("baiya-lora" + lightId, "0.general_onoff")));
        if (resSwitch != null) {
            Integer nodeSwitchInt = (Integer) JSONObject.fromObject(resSwitch).get("value");
            if (nodeSwitchInt == 1)
                nodeSwitch = "n";
            else if (nodeSwitchInt == 0) {
                nodeSwitch = "f";
            }
            System.out.println(nodeSwitch);

        }
        //灯亮度
        String resBrightness = httpPostService.requestPost(httpPostUrl, String.valueOf(deviceService.readDevice("baiya-lora" + lightId, "0.general_level")));
        if (resBrightness != null) {
            String nodeBrightness = JSONObject.fromObject(resBrightness).getString("value");
            System.out.println(nodeBrightness);

        }

    }
}
