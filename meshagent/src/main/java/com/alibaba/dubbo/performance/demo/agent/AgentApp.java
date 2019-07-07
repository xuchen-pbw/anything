package com.alibaba.dubbo.performance.demo.agent;

import com.alibaba.dubbo.performance.demo.agent.utils.Registry;
import com.alibaba.dubbo.performance.demo.agent.utils.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class AgentApp {
    // agent会作为sidecar，部署在每一个Provider和Consumer机器上
    // 在Provider端启动agent时，添加JVM参数-Dtype=provider -Dserver.port=30000 -Ddubbo.protocol.port=20889
    // 在Consumer端启动agent时，添加JVM参数-Dtype=consumer -Dserver.port=20000
    // 添加日志保存目录: -Dlogs.dir=/path/to/your/logs/dir。请安装自己的环境来设置日志目录。

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(AgentApp.class, args);
        String type = System.getProperty("type");
        if (type.equals("consumer")) {
            applicationContext.getBean(Server.class).establish();
            applicationContext.getBean(Registry.class).start();
        } else if (type.equals("provider")) {
            applicationContext.getBean(Server.class).establish();
        }
    }
}
