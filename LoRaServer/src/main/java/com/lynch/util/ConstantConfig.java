package com.lynch.util;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by lynch on 2019/1/18. <br>
 **/
@Component
public class ConstantConfig implements InitializingBean {
    @Value("${com.baiyatech.agent.post.url}")
    private String http_post_url;
    @Value(("${redis.subscribe.command.channel}"))
    private String subscribe_command_channel;
    @Value(("${redis.publish.command.channel}"))
    private String publish_command_channel;
    @Value("${spring.rabbibtmq.topicExchange}")
    private String rabbitmq_topic_exchange;
    @Value("${spring.rabbibtmq.upQueue}")
    private String rabbitmq_up_queue;
    @Value("${spring.rabbibtmq.downQueue}")
    private String rabbitmq_down_queue;
    @Value("${spring.rabbibtmq.upRotingkey}")
    private String rabbitmq_up_routingKey;
    @Value("${spring.rabbibtmq.downRotingkey}")
    private String rabbitmq_down_routingKey;


    public static String httpPostUrl;
    public static String subscribeCommandChannel;
    public static String publishCommandChannel;
    public static String rabbitmqTopicExchange;
    public static String rabbitmqUpQueue;
    public static String rabbitmqDownQueue;
    public static String rabbitmqUpRoutingKey;
    public static String rabbitmqDownRoutingKey;

    @Override
    public void afterPropertiesSet() throws Exception {
        httpPostUrl = http_post_url;
        subscribeCommandChannel = subscribe_command_channel;
        publishCommandChannel = publish_command_channel;
        rabbitmqTopicExchange = rabbitmq_topic_exchange;
        rabbitmqUpQueue = rabbitmq_up_queue;
        rabbitmqDownQueue = rabbitmq_down_queue;
        rabbitmqUpRoutingKey = rabbitmq_up_routingKey;
        rabbitmqDownRoutingKey = rabbitmq_down_routingKey;
    }
}
