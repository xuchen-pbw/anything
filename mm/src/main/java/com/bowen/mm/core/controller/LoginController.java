package com.bowen.mm.core.controller;

import com.bowen.mm.core.service.SessionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {
    //去登陆页面
    @RequestMapping(value = "/login.aspx",method = RequestMethod.GET)
    public String login(){
        return "login";
    }
    //判断用户是否登录
    @RequestMapping(value = "/isLogin.aspx")
    public @ResponseBody
    MappingJacksonValue isLogin(String callback, HttpServletRequest request, HttpServletResponse response){
        Integer result = 0;
        //判断用户是否已经登录
        String username = sessionProvider.getAttributeForUsername();
    }

    @Autowired
    private SessionProvider sessionProvider;
}
