package com.bowen.middleware.pa.PAServer;

import com.bowen.middleware.utils.Server;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class ProviderAgentServer implements Server {
    private ChannelFuture future;
    private ServerBootstrap bootstrap = new ServerBootstrap();
    private int port;

    public ProviderAgentServer(String port){
        this.port = Integer.parseInt(port);
        bootstrap.group(new NioEventLoopGroup(1),new NioEventLoopGroup(8))
                .channel(NioServerSocketChannel.class)
                .childOption(ChannelOption.TCP_NODELAY,true)
                .childHandler(new ProviderServerPipelineInitializer());
    }

    public Channel getChannel(){
        return future.channel();
    }

    public void establish(){
        future = bootstrap.bind(port).syncUninterruptibly();
    }
}
