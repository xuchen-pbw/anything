package com.bowen.mm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
@Controller
@EnableAutoConfiguration
public class MmApplication {

    @RequestMapping("/")
    @ResponseBody
    String home(){
        return "hello spring-boot";
    }
    public static void main(String[] args) {
        SpringApplication.run(MmApplication.class, args);
    }

}
