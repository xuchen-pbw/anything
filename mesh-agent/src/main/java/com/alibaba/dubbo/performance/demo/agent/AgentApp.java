package com.alibaba.dubbo.performance.demo.agent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AgentApp {
    // agent会作为sidecar，部署在每一个Provider和Consumer机器上
    // 在Provider端启动agent时，添加JVM参数-Dtype=provider -Dserver.port=30000 -Ddubbo.protocol.port=20889
    // 在Consumer端启动agent时，添加JVM参数-Dtype=consumer -Dserver.port=20000
    // 添加日志保存目录: -Dlogs.dir=/path/to/your/logs/dir。请安装自己的环境来设置日志目录。
/*
    由于本次比赛是不限语言的，因此仅对 Agent 的能力做出要求。Agent 必须实现如下一些功能：
    服务注册与发现
    负载均衡
    协议转换
    要具有一定的通用性
*/


    public static void main(String[] args) {
        SpringApplication.run(AgentApp.class,args);
    }
}
