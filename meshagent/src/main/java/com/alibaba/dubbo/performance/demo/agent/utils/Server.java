package com.alibaba.dubbo.performance.demo.agent.utils;

import io.netty.channel.Channel;

public interface Server {
    void establish();
    Channel getChannel();

}
