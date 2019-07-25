package com.lynch.handler;

import com.lynch.LoRaServer;
import com.lynch.domain.DownInfoForm;
import com.lynch.domain.DownInfoNodeMap;
import com.lynch.domain.Gateway;
import com.lynch.repository.DeviceRepo;
import com.lynch.repository.GatewayRepo;
import com.lynch.util.ConstructJson;
import com.lynch.util.SpringTool;
import com.lynch.util.base64.base64__;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by lynch on 2019-05-07. <br>
 * 网关一个tcp连接时，上下行数据通信数据处理
 **/
public class MessageTcpHandler extends MessageToMessageDecoder<Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageTcpHandler.class);
    public volatile static String gateway = null;


    DeviceRepo deviceRepo = (DeviceRepo) SpringTool.getBean("deviceRepo");

    GatewayRepo gatewayRepo = (GatewayRepo) SpringTool.getBean("gatewayRepo");

    StringRedisTemplate stringRedisTemplate = (StringRedisTemplate) SpringTool.getBean("stringRedisTemplate");


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.WRITER_IDLE) {
                if (LoRaServer.queueDown.size() != 0) {
                    try {
                        DownInfoNodeMap info = (DownInfoNodeMap) LoRaServer.queueDown.poll(1, TimeUnit.MICROSECONDS);
                        byte[] head = new byte[]{0x02, 0x11, 0x22, 0x03};
                        String headStr = new String(head);
                        synchronized (info) {
                            String nodeId = info.getNodeId();
                            DownInfoForm downInfoForm = info.getDownInfoForm();
                            // 构造 JSON 数据
                            String down = (ConstructJson.ToJsonStr(downInfoForm));
                            //查询绑定到终端对应的网关的ip的channel
                            String gatewayHost = deviceRepo.findGatewayHostByDeviceAddr(nodeId);
                            Channel channel = MessageChannelMap.get(gatewayHost);
                            if (channel == null)
                                LOGGER.info("send message  to gateway ={} fail, gateway is not online！ ", gatewayHost);
                            else {
                                channel.writeAndFlush(Unpooled.copiedBuffer(headStr + down, CharsetUtil.UTF_8))
                                        .addListener((ChannelFuture writeFuture) -> {
                                            //消息发送成功
                                            if (writeFuture.isSuccess()) {
                                                LOGGER.info("send message ={} to gateway success!", down);
                                            }
                                            //消息发送失败
                                            else {
                                                LOGGER.info("send message ={} to gateway fail!", down);

                                            }
                                        });
                                try {
                                    Thread.sleep(100);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    //    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg)
//            throws Exception {
//        ByteBuf byteBuf = (ByteBuf) msg;
//        String received = byteBuf.toString(CharsetUtil.UTF_8);
//        System.out.println(received + "  " + received.length());
//
//    }
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
        stringRedisTemplate.opsForValue().set("gateway:" + gateway, clientIp);
        Gateway findGateway = gatewayRepo.findByGatewayAddr(gateway, clientIp);

        if (findGateway == null) {
            Gateway newGateway = new Gateway();
            newGateway.setGatewayAddr(gateway);
            newGateway.setGatewayHost(clientIp);
            newGateway.setCreateDate(new Date());
            gatewayRepo.save(newGateway);
        } else {
            findGateway.setGatewayAddr(gateway);
            findGateway.setGatewayHost(clientIp);
            gatewayRepo.save(findGateway);
        }


        String received = byteBuf.toString(CharsetUtil.UTF_8).substring(6);

        System.out.println(received + "  " + received.length());

        list.add(received);

        channelHandlerContext.flush();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String clientIp = ((InetSocketAddress) ctx.channel().remoteAddress()).getAddress().getHostAddress();
        //网关地址集合
        stringRedisTemplate.opsForSet().add("gatewayHostList", clientIp);
        LOGGER.info("gateway  which ip = {} channelActive!", clientIp);
        //channel与ip对应缓存
        MessageChannelMap.add(clientIp, (SocketChannel) ctx.channel());

    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel ch = ctx.channel();
        String clientIp = ((InetSocketAddress) ctx.channel().remoteAddress()).getAddress().getHostAddress();
        LOGGER.info("gateway  which ip = {} channelInactive!", clientIp);
        String gatewayAddr = gatewayRepo.findAddrByGatewayHost(clientIp);
        stringRedisTemplate.opsForSet().remove("gatewayHostList", clientIp);
        stringRedisTemplate.delete("gateway:" + gatewayAddr);
        MessageChannelMap.remove((SocketChannel) ctx.channel());
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }


}
