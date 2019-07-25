package com.lynch.connection;

import com.lynch.service.UpInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;


/**
 * Created by lynch on 2018/11/2. <br>
 *     服务bean的配置
 **/
@Configuration
@EnableConfigurationProperties(ConnectionServiceProperties.class)
@ConditionalOnClass(ConnectionService.class)
@ConditionalOnProperty(prefix = "lora-server", value = "enable", matchIfMissing = true)
//@ComponentScan("com.lynch.connection")
public class ConnectionServiceAutoConfiguration {

    @Autowired
    private ConnectionServiceProperties connectionServiceProperties;
    @Autowired
    UpInfoService upInfoService;

    //Server Up and Down
    //udp
    @Bean
    @ConditionalOnMissingBean(ConnectionService.class)
    public ConnectionService connectionService() throws InterruptedException {
        ConnectionService service = new ConnectionService(upInfoService, connectionServiceProperties.getUp_port(), connectionServiceProperties.getDown_port());
        return service;
    }

    //two tcp
    @Bean
    public ConnectionServerService connectionServerService() {
        ConnectionServerService service = new ConnectionServerService(upInfoService, connectionServiceProperties.getUp_port());
        return service;
    }

    @Bean
    public ConnectionClientService connectionClientService() {
        ConnectionClientService service = new ConnectionClientService(connectionServiceProperties.getGateway_host(), connectionServiceProperties.getDown_port());
        return service;
    }

    //one tcp
    @Bean
    public ConnectionTcpService connectionTcpService() {
        ConnectionTcpService service = new ConnectionTcpService(upInfoService, connectionServiceProperties.getUp_port());
        return service;
    }

    //Redis
    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

//    @Bean
//    public RedisTemplate<String, Object> redisTemplate() {
//        final RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
//        template.setConnectionFactory(jedisConnectionFactory());
//        template.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
//        return template;
//    }
//
//    @Bean
//    MessageListenerAdapter messageListener() {
//        return new MessageListenerAdapter(new RedisSubandPub());
//    }
//
//    @Bean
//    RedisMessageListenerContainer redisContainer() {
//        final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
//        container.setConnectionFactory(jedisConnectionFactory());
//        container.addMessageListener(messageListener(), topic());
//        container.setTaskExecutor(Executors.newFixedThreadPool(4));
//        return container;
//    }
//
//    //command channel
//    @Bean
//    ChannelTopic topic() {
//        return new ChannelTopic(ConstantConfig.subscribeCommandChannel);
//    }
}
