package com.alibaba.dubbo.performance.demo.agent.ca.CAServer;

import com.alibaba.dubbo.performance.demo.agent.utils.Client;
import com.alibaba.dubbo.performance.demo.agent.ca.CAClient.ConsumerAgentClient;
import com.alibaba.dubbo.performance.demo.agent.ca.CAClient.Clients;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.netty.handler.codec.http.HttpHeaders.Names.*;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * 发送 任务请求 至 服务提供者
 * 同步阻塞 等到结果返回
 * 构建{@link FullHttpResponse} 返回
 */
public class RequestHandler extends ChannelInboundHandlerAdapter {
    private Logger logger = LoggerFactory.getLogger(RequestHandler.class);


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        FullHttpRequest request = (FullHttpRequest) msg;
        CompositeByteBuf compositeByteBuf = Unpooled.compositeBuffer();
        long reqID = RequestHolder.getId();
        request.content().skipBytes(136);
        compositeByteBuf
                .addComponents(true,
                        Unpooled.copyLong(reqID),
                        request.content().slice());
        Promise promise = new DefaultPromise<Object>(ctx.executor());
        Client client = Clients.next();
        if (!client.isStart()) {
            synchronized (ConsumerAgentClient.class) {
                if (!client.isStart()) {
                    client.start();
                }
            }
        }
        client.getChannel().writeAndFlush(compositeByteBuf).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if(channelFuture.isSuccess()){
                    System.out.println("success");
                }
            }
        });
        RequestHolder.holder.put(reqID, promise);

        promise.addListener(new GenericFutureListener<Promise<Integer>>() {
            @Override
            public void operationComplete(Promise future) throws Exception {
                ByteBuf result = (ByteBuf) future.get();
                Clients.RequestComplete(client);
                FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1,
                        OK, result);
                response.headers().set(CONTENT_TYPE, "text/plain");
                response.headers().set(CONTENT_LENGTH,
                        response.content().readableBytes());
                response.headers().set(CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
                ctx.write(response);
                ctx.flush();
            }
        });

    }

}
