package com.lynch.connection;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoop;

import java.util.concurrent.TimeUnit;

/**
 * Created by lynch on 2019-04-09. <br>
 * ConnectionListener 负责启动时重连5秒后重连与下行队列监听
 **/
public class ConnectionClientListener implements ChannelFutureListener {
    private ConnectionClientService client;

    public ConnectionClientListener(ConnectionClientService client) {
        this.client = client;
    }

    @Override
    public void operationComplete(ChannelFuture channelFuture) throws Exception {
        if (!channelFuture.isSuccess()) {
            System.out.println("下行重连中。。。");
            final EventLoop loop = channelFuture.channel().eventLoop();
            loop.schedule(new Runnable() {
                @Override
                public void run() {
                    client.createBootstrap(new Bootstrap(), loop);
                }
            }, 5L, TimeUnit.SECONDS);
        } else {
            System.out.println("连接成功");
        }
    }
}