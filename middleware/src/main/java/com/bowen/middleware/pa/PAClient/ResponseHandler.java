package com.bowen.middleware.pa.PAClient;

import com.bowen.middleware.utils.model.RpcResponse;
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
        logger.warn("disconnect form provider");
        super.channelInactive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcResponse response) {
        CompositeByteBuf compositeByteBuf = Unpooled.compositeBuffer();
        compositeByteBuf.addComponents(true,
                Unpooled.copyLong(Long.parseLong(response.getRequestId())),
                Unpooled.wrappedBuffer(response.getBytes()));
        Channel channel = Clients.getServer();
        channel.writeAndFlush(compositeByteBuf);
    }
}
