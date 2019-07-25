package com.lynch.rabbit;

import com.lynch.service.LightService;
import net.sf.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Created by lynch on 2019/1/17. <br>
 **/
@Component
public class RabbitMqReceiver {
    @Autowired
    private LightService lightService;
    @Autowired
    private RabbitMqSender rabbitMqSender;

    @RabbitListener(queues = {"lora_down"})
    public void process(byte[] messageByte) {
        JSONObject messageJson = JSONObject.fromObject(new String(messageByte));
        String type = messageJson.getString("type");
        System.out.println(messageJson);
        switch (type) {
            //下行命名
            case "write.req":
                String command = messageJson.getString("key");
                String controlDeviceId = messageJson.getString("device_id");
                String value = messageJson.getString("value");
                switch (command) {
                    //开关灯
                    case "0.general_onoff":
                        if (value.equals("1"))
                            value = "n";
                        else if (value.equals("0")) {
                            value = "f";
                        }
                        JSONObject downMessage = new JSONObject();
                        downMessage.put("nodeId", controlDeviceId);
                        downMessage.put("nodeswitch", value);
                        lightService.downControl(controlDeviceId,downMessage);
                        //正式使用时放在网关发来上行
                        rabbitMqSender.writeResp(controlDeviceId, value);
                        break;
                    default:
                        System.out.println("unknown command");
                        break;
                }
                break;
            case "getDeviceMany.req":
                String deviceIds = messageJson.getString("device_ids");
                rabbitMqSender.getDeviceManyResp(deviceIds);
                break;
            case "getDevice.req":
                String deviceId = messageJson.getString("device_id");
                rabbitMqSender.getDeviceResp(deviceId);
                break;
            default:
                System.out.println("unknown type");
                break;
        }

    }

}
