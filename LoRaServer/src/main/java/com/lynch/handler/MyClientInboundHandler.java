package com.lynch.handler;

import com.lynch.connection.ConnectionClientService;
import com.lynch.util.base64.base64__;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoop;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.TimeUnit;

/**
 * Created by lynch on 2019-04-09. <br>
 * 与网关两个tcp连接时，服务器作为客户端，ChannelHandler监测连接是否断掉，断掉的话也要重连
 **/
public class MyClientInboundHandler extends SimpleChannelInboundHandler<ByteBuf> {


    private ConnectionClientService client;

    public MyClientInboundHandler(ConnectionClientService client) {
        this.client = client;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("链路中断，下行重连中。。。");
        final EventLoop eventLoop = ctx.channel().eventLoop();
        eventLoop.schedule(new Runnable() {
            @Override
            public void run() {
                client.createBootstrap(new Bootstrap(), eventLoop);
            }
        }, 1L, TimeUnit.SECONDS);
        super.channelInactive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
//        System.out.println("Client received: " + byteBuf.toString(CharsetUtil.UTF_8));
        byte[] receive = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(receive);
        System.out.println("Client received: " + base64__.apptohex(receive));

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        // TODO Auto-generated method stub
        super.exceptionCaught(ctx, cause);
    }
}
