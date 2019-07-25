package com.lynch.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lynch on 2018/11/19. <br>
 **/
public class JsonGetdata {
    public static double lsnr = 0;
    public static String datr = null;

    public static void getjsondata(String jsonstr) {
        try {
            JSONObject jsonObject = new JSONObject(jsonstr);
            if (!jsonObject.isNull("rxpk")) {
                JSONArray rxpk_arry = jsonObject.getJSONArray("rxpk");
                {
                    for (int i = 0; i < rxpk_arry.length(); i++) {
                        lsnr = rxpk_arry.getJSONObject(i).getDouble("lsnr");
                        datr = rxpk_arry.getJSONObject(i).getString("datr");
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
