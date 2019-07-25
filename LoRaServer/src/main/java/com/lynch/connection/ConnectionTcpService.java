package com.lynch.connection;

import com.lynch.handler.*;
import com.lynch.service.UpInfoService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.TimeUnit;

/**
 * Created by lynch on 2019-05-07. <br>
 *    与网关一个tcp连接的通信
 **/
public class ConnectionTcpService {
    private EventLoopGroup bossGroup = null;
    private EventLoopGroup workerGroup = null;
    private ServerBootstrap bootstrap = null;
    private int up_port;
    UpInfoService upInfoService;

    public ConnectionTcpService(UpInfoService upInfoService, int up_port) {
        this.bossGroup = new NioEventLoopGroup();
        this.workerGroup = new NioEventLoopGroup();
        this.up_port = up_port;
        this.bootstrap = new ServerBootstrap();
        this.upInfoService = upInfoService;
    }

    @Async
    public void start() {
        try {
            ByteBuf []delimiters = new ByteBuf[2];
            delimiters[0] = Unpooled.copiedBuffer("}]}".getBytes());
            delimiters[1] = Unpooled.copiedBuffer("}}".getBytes());
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline
                                    .addLast(new IdleStateHandler(250, 250, 2000, TimeUnit.MILLISECONDS))
//                                    .addLast(new FixedLengthFrameDecoder(256))//定长解决粘包
//                                    .addLast(new DelimiterBasedFrameDecoder(1024, false,Unpooled.copiedBuffer("}]}".getBytes())))//特殊分隔符解决分包
                                    .addLast(new DelimiterBasedFrameDecoder(1024, false,delimiters))//特殊分隔符解决分包
                                    .addLast("message", new MessageTcpHandler())
                                    .addLast("parse", new ParseJsonHandler())
                                    .addLast("persistData", new PersistDataHandler(upInfoService))
                                    .addLast("macData", new MacDataHandler());
                        }
                    });

            // Start the server.
            ChannelFuture f = bootstrap.bind(up_port).sync();
            if (f.isSuccess()) {
                System.out.println("监听已打开");
            }

            // Wait until the server socket is closed.
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
