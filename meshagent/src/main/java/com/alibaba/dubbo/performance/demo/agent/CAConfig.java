package com.alibaba.dubbo.performance.demo.agent;

import com.alibaba.dubbo.performance.demo.agent.utils.Registry;
import com.alibaba.dubbo.performance.demo.agent.utils.Server;
import com.alibaba.dubbo.performance.demo.agent.ca.CAServer.ConsumerAgentServer;
import com.alibaba.dubbo.performance.demo.agent.ca.ConsumerAgentRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("consumer")
public class CAConfig {
    @Bean
    public Server consumerAgentServer() {
        return new ConsumerAgentServer(System.getProperty("server.port"));
    }

//    @Bean
//    public Client client() {
//        return new ConsumerAgentClient("localhost", 30000);
//    }

    @Bean
    public Registry consumerAgentRegistry() {
        return new ConsumerAgentRegistry();
    }
}
