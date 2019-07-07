package com.alibaba.dubbo.performance.demo.agent.ca.CAClient;

import com.alibaba.dubbo.performance.demo.agent.ca.CAServer.RequestHolder;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 解析 任务ID  notify对应阻塞EventLoop
 */
public class ResponseHandler extends ChannelInboundHandlerAdapter {
    private Logger logger = LoggerFactory.getLogger(ResponseHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        long id = byteBuf.readLong();
        ByteBuf result = byteBuf.slice();
        RequestHolder.holder.get(id).trySuccess(result);
        RequestHolder.holder.remove(id);
    }


//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        RequestProto.Request.Builder builder = RequestProto.Request.newBuilder();
//        builder.setId(1)
//                .setInterfaceName("no name")
//                .setMethodName("no method")
//                .setParameter("hahah")
//                .setParameterTypesString("asd");
//        ctx.writeAndFlush(builder.build());
//
//    }
}
