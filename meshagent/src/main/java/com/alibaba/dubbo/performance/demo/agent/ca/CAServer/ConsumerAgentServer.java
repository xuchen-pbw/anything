package com.alibaba.dubbo.performance.demo.agent.ca.CAServer;

import com.alibaba.dubbo.performance.demo.agent.utils.Server;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class ConsumerAgentServer implements Server {
    private ServerBootstrap bootstrap = new ServerBootstrap();
    private int port;

    public ConsumerAgentServer(String port) {
        this.port = Integer.parseInt(port);
        bootstrap.group(new NioEventLoopGroup(1), new NioEventLoopGroup(256))
                .channel(NioServerSocketChannel.class)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ConsumerServerPipelineInitializer());
    }

    public void establish() {
        bootstrap.bind(port).syncUninterruptibly();
    }

    @Override
    public Channel getChannel() {
        return null;
    }
}

