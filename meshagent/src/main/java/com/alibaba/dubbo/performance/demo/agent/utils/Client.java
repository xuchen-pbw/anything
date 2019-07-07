package com.alibaba.dubbo.performance.demo.agent.utils;

import io.netty.channel.Channel;

public interface Client {
    Channel getChannel();

    void start();

    boolean isStart();
}
