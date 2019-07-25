//package com.lynch.mqtt;
//
//import org.springframework.integration.annotation.MessagingGateway;
//import org.springframework.integration.mqtt.support.MqttHeaders;
//import org.springframework.messaging.handler.annotation.Header;
//
///**
// * Created by lynch on 2019-03-31. <br>
// **/
//@MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
//public interface MqttGateway {
//    void mqttPublishMessage(String data,@Header(MqttHeaders.TOPIC) String topic);
//}
