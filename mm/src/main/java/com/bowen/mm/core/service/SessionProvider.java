package com.bowen.mm.core.service;

/**
 * @author non
 * @version 1.0
 * @date 2019/7/25 21:52
 */
public interface SessionProvider {
    //先行提供接口
    //保存用户名到Redis中
    public void setAttributeForUsername(String name, String value);

    //从Redis中取用户名
    public String getAttributeForUsername(String name);

    //验证码

    //退出登录
}
