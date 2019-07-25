package com.lynch;

import com.lynch.rabbit.RabbitMqSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by lynch on 2019/1/17. <br>
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = LoRaServer.class)

public class RabbitTest {
    @Autowired
    private RabbitMqSender sender;

    @Test
    public void hello() throws Exception {
        sender.writeDeviceString("96-bf-df-01","0.general_onoff","1");
    }
}
