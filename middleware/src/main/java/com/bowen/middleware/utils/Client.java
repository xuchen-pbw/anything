package com.bowen.middleware.utils;

import io.netty.channel.Channel;

public interface Client {

    Channel getChannel();

    void start();

    boolean isStart();
}
