package com.lynch.controller;


import com.lynch.service.LightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by lynch on 2019/1/7. <br>
 * 接收agent的下行数据
 **/
@RestController
@RequestMapping(value = "/msg")
public class DeviceControlController {
    @Autowired
    private LightService lightService;

    @PostMapping
    public void getAgentMsg(@RequestBody String body) {
        lightService.handlerLightFromAgent(body);


    }
}
