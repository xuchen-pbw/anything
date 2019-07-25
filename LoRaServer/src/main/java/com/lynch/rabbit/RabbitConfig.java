package com.lynch.rabbit;

import com.lynch.util.ConstantConfig;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by lynch on 2019/1/17. <br>
 * 如果一个队列绑定的键为"#"时，将会接收所有的队列，类似于fanout转发器。
 * 如果绑定的队列不包含"#"和"*"时，这时候类似于Direct模式，直接匹配。
 **/
@Configuration
public class RabbitConfig {

    //队列
    @Bean(name = "queueUp")
    public Queue loraUpQueue() {

        return new Queue(ConstantConfig.rabbitmqUpQueue);
    }

    //队列
    @Bean(name = "queueDown")
    public Queue loraDownQueue() {

        return new Queue(ConstantConfig.rabbitmqDownQueue);
    }


    //创建交换机
    //topic策略
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(ConstantConfig.rabbitmqTopicExchange);
    }


    //绑定队列
    @Bean
    Binding bindingUp(@Qualifier("queueUp") Queue queueUp, TopicExchange exchange) {
        return BindingBuilder.bind(queueUp).to(exchange).with(ConstantConfig.rabbitmqUpRoutingKey);
    }

    //绑定队列
    @Bean
    Binding bindingDown(@Qualifier("queueDown") Queue queueDown, TopicExchange exchange) {
        return BindingBuilder.bind(queueDown).to(exchange).with(ConstantConfig.rabbitmqDownRoutingKey);
    }

}