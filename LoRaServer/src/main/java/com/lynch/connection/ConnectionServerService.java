package com.lynch.connection;

import com.lynch.handler.*;
import com.lynch.service.UpInfoService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import org.springframework.scheduling.annotation.Async;

/**
 * Created by lynch on 2019-04-08. <br>
 *     与网关两个tcp连接中的netty服务端
 **/
public class ConnectionServerService {
    private EventLoopGroup bossGroup = null;
    private EventLoopGroup workerGroup = null;
    private ServerBootstrap bootstrap = null;
    private int up_port;
    UpInfoService upInfoService;

    public ConnectionServerService(UpInfoService upInfoService, int up_port) {
        this.bossGroup = new NioEventLoopGroup();
        this.workerGroup = new NioEventLoopGroup();
        this.up_port = up_port;
        this.bootstrap = new ServerBootstrap();
        this.upInfoService = upInfoService;
    }

    @Async
    public void start() {
        try {
            bootstrap.group(bossGroup, workerGroup);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline
//                            .addLast(new FixedLengthFrameDecoder(256))//定长解决粘包
                            .addLast(new DelimiterBasedFrameDecoder(1024, false, Unpooled.copiedBuffer("}]}".getBytes())))//特殊分隔符解决分包
                            .addLast("message", new MessageServerHandler())
                            .addLast("parse", new ParseJsonHandler())
                            .addLast("persistData", new PersistDataHandler(upInfoService))
                            .addLast("macData", new MacDataHandler());
                }
            });

            // Start the server.
            ChannelFuture f = bootstrap.bind(up_port).sync();
            if (f.isSuccess()) {
                System.out.println("上行监听已打开");
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
