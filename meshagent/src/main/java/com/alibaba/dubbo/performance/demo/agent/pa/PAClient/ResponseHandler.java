package com.alibaba.dubbo.performance.demo.agent.pa.PAClient;

import com.alibaba.dubbo.performance.demo.agent.utils.model.RpcResponse;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResponseHandler extends SimpleChannelInboundHandler<RpcResponse> {
    private Logger logger = LoggerFactory.getLogger(ResponseHandler.class);

    @Override
    public boolean acceptInboundMessage(Object msg) throws Exception {
        return super.acceptInboundMessage(msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("connect to provider");
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.warn("disconnect from provider");
        super.channelInactive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcResponse response) {

//        ResponseProto.Response.Builder builder = ResponseProto.Response.newBuilder();
//        builder.setId(Long.parseLong(response.getRequestId()));
//        builder.setResult(Integer.parseInt(new String(response.getBytes()).trim()));
//        builder.setStatus(0);
//        ResponseProto.Response resp = builder.build();
        CompositeByteBuf compositeByteBuf = Unpooled.compositeBuffer();
        compositeByteBuf.addComponents(true,
                Unpooled.copyLong(Long.parseLong(response.getRequestId())),
                Unpooled.wrappedBuffer(response.getBytes()));
        Channel channel = Clients.getServer();
        channel.writeAndFlush(compositeByteBuf);
    }
}
