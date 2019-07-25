package com.lynch;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;

/**
 * Created by lynch on 2019/3/12. <br>
 **/
public class MadeJson {
    public static void main(String[] args) {
        //{
        //    "type":"register.req",
        //    "device_id":"baiya-loratest123456",
        //    "data":
        //    {
        //        "keys":
        //        {
        //            "product_manufacturer_description":["HeBei BaiYa technology",1],
        //            "product_manufacturer":["baiyatech.com",1],
        //            "product_model":["LORA-LIGHT",1]},
        //        "modules":
        //        [
        //            {
        //                "keys":
        //                {
        //                    "general_level":[null,17],
        //                    "general_onoff":[0,27]
        //                }
        //            }
        //        ]
        //    }
        //}
        String deviceId = "11:33:55:77";
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
        System.out.println(response);

    }
}
