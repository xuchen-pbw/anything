package com.bowen.middleware.pa.PAClient;

import com.bowen.middleware.utils.Client;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class ProviderAgentClient implements Client {
    private AtomicInteger count = new AtomicInteger(0);
    private int port;
    private static ArrayList<ChannelFuture> future = new ArrayList<>(2);
    private Bootstrap bootstrap;
    private volatile boolean start = false;

    public ProviderAgentClient(String port){
        this.port = Integer.parseInt(port);
        bootstrap = new Bootstrap();
        bootstrap.group(new NioEventLoopGroup(2))
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY,true)
                .handler(new ProviderClientPipelineInitializer());
        Clients.setClient(this);
    }

    public void start(){
        future.add(bootstrap.connect("localhost",port).syncUninterruptibly());
        future.add(bootstrap.clone().connect("localhost",port).syncUninterruptibly());
        this.start = true;
    }

    public Channel getChannel(){
        return future.get(count.getAndIncrement() & 1).channel();
    }

    public boolean isStart(){
        return start;
    }
}
