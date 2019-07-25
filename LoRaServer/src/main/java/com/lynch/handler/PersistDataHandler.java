package com.lynch.handler;

import com.lynch.domain.UpInfoForm;
import com.lynch.service.UpInfoService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Map;

/**
 * Created by lynch on 2019/1/7. <br>
 * 持久化终端与网关的数据到mysql数据库
 **/
public class PersistDataHandler extends ChannelInboundHandlerAdapter {
    private UpInfoService service;

    public PersistDataHandler(UpInfoService service) {
        this.service = service;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        System.out.println("channelRead() from persist");

    }


    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
//        System.out.println("channelRead() from reg");

    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        System.out.println("channelactead() from persist");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        System.out.println("channelReadCCCC() from persist");
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        super.channelWritabilityChanged(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        Map<String, UpInfoForm> upInfoFormMap = (Map<String, UpInfoForm>) evt;
        System.out.println("**************************");
        for (Map.Entry<String, UpInfoForm> entry : upInfoFormMap.entrySet()) {
            System.out.println(entry.getValue().getSysInfo());
            System.out.println("**************************");
            service.save(entry.getValue());
        }
//        只处理lora，丢弃其他的
        ctx.fireUserEventTriggered(upInfoFormMap.get("loraendpkt"));
    }
}
