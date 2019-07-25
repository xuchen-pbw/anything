package com.lynch.connection;


import com.lynch.handler.MessageClientHandler;
import com.lynch.handler.MyClientInboundHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.TimeUnit;


/**
 * Created by lynch on 2019-04-08. <br>
 *     与网关两个tcp连接中的netty客户端
 *
 **/
public class ConnectionClientService {
    private EventLoopGroup group = null;
    private String gateway_host;
    private Integer down_port;


    public ConnectionClientService(String gateway_host, int down_port) {
        this.group = new NioEventLoopGroup();
        this.gateway_host = gateway_host;
        this.down_port = down_port;

    }

    @Async
    public void start() {
        createBootstrap(new Bootstrap(), group);
    }

    public Bootstrap createBootstrap(Bootstrap bootstrap, EventLoopGroup eventLoop) {

        if (bootstrap != null) {
            final MyClientInboundHandler handler = new MyClientInboundHandler(this);
            bootstrap.group(eventLoop);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline()
                            .addLast(handler)
                            .addLast(new IdleStateHandler(250, 250, 2000, TimeUnit.MILLISECONDS))
                            .addLast("tcpClient", new MessageClientHandler());
                }
            });
            bootstrap.remoteAddress(gateway_host, down_port);
            bootstrap.connect().addListener(new ConnectionClientListener(this));


        }
        return bootstrap;
    }
}
