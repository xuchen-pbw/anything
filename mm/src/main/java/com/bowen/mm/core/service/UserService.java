package com.bowen.mm.core.service;

import com.bowen.mm.core.bean.User;

/**
 * @author non
 * @version 1.0
 * @date 2019/7/25 22:01
 */
public interface UserService {
    //通过用户名查询用户对象
    public User selectUserByUsername(String username);

}
