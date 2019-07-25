package com.lynch.rabbit;


import com.alibaba.fastjson.JSONArray;
import com.lynch.repository.LightRepo;
import com.lynch.util.ConstantConfig;
import net.sf.json.JSONObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;


/**
 * Created by lynch on 2019/1/17. <br>
 **/
@Component
public class RabbitMqSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private LightRepo lightRepo;

    /**
     * 回复write.req
     * 用于终端设备下行控制
     *
     * @param deviceId
     * @param value
     */
    public void writeResp(String deviceId, String value) {
        JSONObject respMessage = new JSONObject();
        respMessage.put("type", "write.resp");
        respMessage.put("device_id", deviceId);
        if (value.equals("n"))
            value = "1";
        else if (value.equals("f")) {
            value = "0";
        }
        respMessage.put("value", value);
        respMessage.put("key", "0.general_onoff");
        rabbitTemplate.convertAndSend(ConstantConfig.rabbitmqTopicExchange, ConstantConfig.rabbitmqUpRoutingKey, String.valueOf(respMessage).getBytes());
    }


    public void getDeviceResp(String deviceId) {
        JSONObject respMessage = new JSONObject();
        JSONObject data = new JSONObject();
        JSONObject keys = new JSONObject();
        JSONArray modules = new JSONArray();
        JSONObject modules_keys = new JSONObject();
        JSONObject m_keys_value = new JSONObject();
        respMessage.put("type", "getDevice.resp");
        respMessage.put("device_id", deviceId);
        String onoff = lightRepo.getLightSwitch(deviceId);
        if (onoff != null) {
            if (onoff.equals("n") || onoff.equals("u")) {
                onoff = "1";
            } else if (onoff.equals("d") || onoff.equals("f")) {
                onoff = "0";
            }
            respMessage.put("status", "1/OK");
            ArrayList<Object> networkIsOnline = new ArrayList<>();
            networkIsOnline.add(1);
            keys.put("network_isonline", networkIsOnline);
            data.put("keys", keys);
            ArrayList<Object> generalOnoff = new ArrayList<>();
            generalOnoff.add(onoff);
            m_keys_value.put("general_onoff", generalOnoff);
            modules_keys.put("keys", m_keys_value);
            modules.add(modules_keys);
            data.put("modules", modules);
            respMessage.put("data", data);
            rabbitTemplate.convertAndSend(ConstantConfig.rabbitmqTopicExchange, ConstantConfig.rabbitmqUpRoutingKey, String.valueOf(respMessage).getBytes());

        }

    }

    public void getDeviceManyResp(String deviceIds) {
        JSONObject respMessage = new JSONObject();
        JSONArray data = new JSONArray();
        JSONObject keys = new JSONObject();
        ArrayList<Object> ids = new ArrayList<>();
        respMessage.put("type", "getDeviceMany.resp");
        respMessage.put("status", "1/OK");
        if (deviceIds.equals("[]")) {
            respMessage.put("device_ids", ids);
            respMessage.put("data", data);
        } else {
            String deviceList = deviceIds.substring(deviceIds.indexOf("[") + 1, deviceIds.lastIndexOf("]"));
            String[] deviceIdArray = deviceList.split(",");
            for (int i = 0; i < deviceIdArray.length; i++) {
                JSONObject singleData = new JSONObject();
                String deviceId = deviceIdArray[i].replace("\"", "");//去掉字符串的双引号
                singleData.put("device_id", deviceId);
                ids.add(deviceId);
                ArrayList<Object> networkIsOnline = new ArrayList<>();
                networkIsOnline.add(1);
                keys.put("network_isonline", networkIsOnline);
                singleData.put("keys", keys);
                data.add(singleData);
            }
            respMessage.put("device_ids", ids);
            respMessage.put("data", data);
            rabbitTemplate.convertAndSend(ConstantConfig.rabbitmqTopicExchange, ConstantConfig.rabbitmqUpRoutingKey, String.valueOf(respMessage).getBytes());

        }


    }

    public void writeDeviceString(String deviceId, String key, String value) {
        JSONObject response = new JSONObject();
        response.put("type", "write.req");
        response.put("device_id", deviceId);
        response.put("key", key);
        response.put("value", value);
        rabbitTemplate.convertAndSend(ConstantConfig.rabbitmqTopicExchange, "baiya.VDM.I." + deviceId + ".write", String.valueOf(response).getBytes());
    }

}