package com.lynch.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by lynch on 2019/3/11. <br>
 **/
@Service
public class DeviceService {

//    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceService.class);


    /**
     * 设备注册
     *
     * @param deviceId
     * @return
     */
    public JSONObject registerDevice(String deviceId) {
        JSONObject response = new JSONObject();

        response.put("type", "register.req");
        response.put("device_id", "baiya-lora" + deviceId);

        JSONObject dataRes = new JSONObject();

        JSONObject keys = new JSONObject();

        ArrayList<Object> productManufacturerM = new ArrayList<>();
        productManufacturerM.add("HeBei BaiYa technology");
        productManufacturerM.add(1);
        keys.put("product_manufacturer_description", productManufacturerM);

        ArrayList<Object> productManufacturer = new ArrayList<>();
        productManufacturer.add("baiyatech.com");
        productManufacturer.add(1);
        keys.put("product_manufacturer", productManufacturer);

        ArrayList<Object> productModel = new ArrayList<>();
        productModel.add("LORA-LIGHT");
        productModel.add(1);
        keys.put("product_model", productModel);

        dataRes.put("keys", keys);

        JSONArray modules = new JSONArray();

        JSONObject modules_keys = new JSONObject();

        JSONObject m_keys_value = new JSONObject();

        ArrayList<Object> generalLevel = new ArrayList<>();
        generalLevel.add(null);
        generalLevel.add(17);
        m_keys_value.put("general_level", generalLevel);

        ArrayList<Object> generalOnoff = new ArrayList<>();
        generalOnoff.add(0);
        generalOnoff.add(27);
        m_keys_value.put("general_onoff", generalOnoff);

        modules_keys.put("keys", m_keys_value);

        modules.add(modules_keys);

        dataRes.put("modules", modules);

        response.put("data", dataRes);

        return response;
    }

    /**
     * 读取灯状态
     *
     * @param deviceId
     * @return
     */
    public JSONObject readDevice(String deviceId, String key) {
        JSONObject response = new JSONObject();
        response.put("type", "read.req");
        response.put("device_id", deviceId);
        response.put("key", key);//0.general_onoff或//0.general_level
        return response;
    }

    /**
     * 写入灯状态
     *
     * @param deviceId
     * @return
     */
    public JSONObject writeDeviceString(String deviceId, String key, String value) {
        JSONObject response = new JSONObject();
        response.put("type", "write.req");
        response.put("device_id", deviceId);
        response.put("key", key);
        response.put("value", value);
        return response;
    }

    /**
     * 写入灯状态
     *
     * @param deviceId
     * @return
     */
    public JSONObject writeDeviceInteger(String deviceId, String key, Integer value) {
        JSONObject response = new JSONObject();
        response.put("type", "write.req");
        response.put("device_id", deviceId);
        response.put("key", key);
        response.put("value", value);
        return response;
    }

}
