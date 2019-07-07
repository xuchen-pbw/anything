package com.alibaba.dubbo.performance.demo.agent;

import com.alibaba.dubbo.performance.demo.agent.utils.Client;
import com.alibaba.dubbo.performance.demo.agent.utils.Registry;
import com.alibaba.dubbo.performance.demo.agent.utils.Server;
import com.alibaba.dubbo.performance.demo.agent.pa.PAClient.ProviderAgentClient;
import com.alibaba.dubbo.performance.demo.agent.pa.PAServer.ProviderAgentServer;
import com.alibaba.dubbo.performance.demo.agent.pa.ProviderAgentRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("provider")
public class PAConfig {
    @Bean
    public Registry providerAgentRegistry() {
        return new ProviderAgentRegistry();
    }

    @Bean
    public Server providerAgentServer() {
        return new ProviderAgentServer(System.getProperty("server.port"));
    }

    @Bean
    public Client providerAgentClient() {
        return new ProviderAgentClient(System.getProperty("dubbo.protocol.port"));
    }


}
