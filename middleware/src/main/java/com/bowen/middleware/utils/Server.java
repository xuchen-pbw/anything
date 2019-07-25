package com.bowen.middleware.utils;

import io.netty.channel.Channel;

public interface Server {
    void establish();
    Channel getChannel();
}
