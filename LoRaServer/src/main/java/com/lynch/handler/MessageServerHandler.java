package com.lynch.handler;

import com.lynch.util.SpringTool;
import com.lynch.util.base64.base64__;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.CharsetUtil;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Set;

/**
 * Created by lynch on 2019-04-08. <br>
 * 与网关两个tcp连接时，服务器作为服务端的通信数据处理
 **/
public class MessageServerHandler extends MessageToMessageDecoder<Object> {
    public volatile static String gateway = null;

    StringRedisTemplate stringRedisTemplate = (StringRedisTemplate) SpringTool.getBean("stringRedisTemplate");

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, Object msg, List<Object> list) throws Exception {
        String clientIp = ((InetSocketAddress) channelHandlerContext.channel().remoteAddress()).getAddress().getHostAddress();
        ByteBuf byteBuf = (ByteBuf) msg;

        ByteBuf phyUp = byteBuf.copy(0, 12);
        byte[] pyhUpByte = new byte[phyUp.readableBytes()];
        phyUp.readBytes(pyhUpByte);
        //网关地址
        byte[] gatewayByte = new byte[8];
        System.arraycopy(pyhUpByte, 4, gatewayByte, 0, 8);
        gateway = base64__.BytetoHex(gatewayByte);
        //网关与ip映射
        stringRedisTemplate.opsForValue().set("gateway:" + gateway + ":" + clientIp, clientIp);
        //网关集合
        stringRedisTemplate.opsForSet().add("gatewayList", gateway);


        String received = byteBuf.toString(CharsetUtil.UTF_8).substring(6);
        System.out.println("Server received: " + received);

        ByteBuf ackBuf = byteBuf.copy(0, 4);
        ackBuf.setByte(3, 0x01);
        channelHandlerContext.write(Unpooled.copiedBuffer(ackBuf));
//        channelHandlerContext.write(Unpooled.copiedBuffer("lora服务器收到", CharsetUtil.UTF_8));

        list.add(received);

        channelHandlerContext.flush();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        String clientIp = ((InetSocketAddress) ctx.channel().remoteAddress()).getAddress().getHostAddress();
        System.out.println(clientIp + " channelInactive");
        Set<String> gatewayAddrIPSet = stringRedisTemplate.keys("gateway:" + "*" + clientIp);
        for (String gatewayAddrIP : gatewayAddrIPSet) {
            stringRedisTemplate.opsForSet().remove("gatewayList", gatewayAddrIP.substring(8, gatewayAddrIP.lastIndexOf(":")));
            stringRedisTemplate.delete(gatewayAddrIP);
            stringRedisTemplate.delete(gatewayAddrIP);

        }
    }
//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        String clientIp = ((InetSocketAddress) ctx.channel().remoteAddress()).getAddress().getHostAddress();
//        ConnectionClientService connectionClientService = new ConnectionClientService(clientIp,1782);
//        final EventLoop eventLoop = ctx.channel().eventLoop();
//        connectionClientService.createBootstrap(new Bootstrap(),eventLoop);
//        //网关(客户端）ip集合
//        stringRedisTemplate.opsForSet().add("gatewayHostList", clientIp);
//
//        System.out.println(clientIp + " channelActive");
//    }
}
