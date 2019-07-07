package com.alibaba.dubbo.performance.demo.agent.ca.CAClient;

import com.alibaba.dubbo.performance.demo.agent.utils.Client;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class ConsumerAgentClient implements Client {
    private AtomicInteger count = new AtomicInteger(0);
    private Bootstrap bootstrap = new Bootstrap();
    private ArrayList<ChannelFuture> future = new ArrayList<>(8);
    private String host;
    private int port;
    private volatile boolean start = false;

    public ConsumerAgentClient(String host, int port) {
        bootstrap.group(new NioEventLoopGroup(8))
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ConsumerClientPipelineInitializer());
        this.host = host;
        this.port = port;
        Clients.addClient(this);
    }


    public void start() {
        future.add(bootstrap.connect(host, port));
        for (int i = 0; i < 7; i++) {
            future.add(bootstrap.clone().connect(host, port));
        }
        start = true;
    }

    @Override
    public boolean isStart() {
        return start;
    }

    public Channel getChannel() {
        int c = count.getAndIncrement();

        return future.get(c & 3).channel();
    }
}
