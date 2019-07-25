package com.bowen.mm.core.service.impl;

import com.bowen.mm.core.service.SessionProvider;
import com.bowen.mm.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

/**
 * @author non
 * @version 1.0
 * @date 2019/7/25 22:11
 */
public class SessionProviderImpl implements SessionProvider {
    @Autowired
    private Jedis jedis;
    private Integer exp = 30;

    public void setExp(Integer exp){
        this.exp = exp;
    }

    @Override
    public void setAttributeForUsername(String name, String value) {
        //保持用户名到Redis中
        jedis.set(name + ":" + Constants.USER_NAME, value);
        //时间
        jedis.expire(name + ":" + Constants.USER_NAME, 60*exp);
    }

    @Override
    public String getAttributeForUsername(String name) {
        String value = jedis.get(name + ":" + Constants.USER_NAME);
        if (null != value){
            jedis.expire(name + ":" + Constants.USER_NAME,60*exp);
        }
        return value;
    }
}
