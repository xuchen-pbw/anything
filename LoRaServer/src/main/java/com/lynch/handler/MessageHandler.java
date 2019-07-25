package com.lynch.handler;

import com.lynch.LoRaServer;
import com.lynch.domain.DownInfoForm;
import com.lynch.util.ConstructJson;
import com.lynch.util.base64.base64__;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by lynch on 2018/11/2. <br>
 * 与网关udp连接时，处理上下行通信数据
 **/
public class MessageHandler extends MessageToMessageDecoder<DatagramPacket> {
    private static final byte PKT_PUSH_DATA = 0x00;
    private static final byte PKT_PUSH_ACK = 0x01;
    private static final byte PKT_PULL_DATA = 0x02;
    private static final byte PKT_PULL_RESP = 0x03;
    private static final byte PKT_PULL_ACK = 0x04;

    public volatile static String gateway = null;

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageHandler.class);


    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket, List<Object> list) throws Exception {
//        System.out.println(">>>>>>>received package:");
//        //打印收到的报文内容
//        System.out.println(ByteBufUtil.hexDump(datagramPacket.content()));
        //获取gateway Mac Address
        ByteBuf gatewayMacAddr = datagramPacket.content().copy(4, 8);
        byte[] gatewayByte = new byte[gatewayMacAddr.readableBytes()];
        gatewayMacAddr.readBytes(gatewayByte);
        gateway = base64__.BytetoHex(gatewayByte);
        gatewayMacAddr.release();
        switch (datagramPacket.content().getByte(3)) {
            case PKT_PUSH_DATA:
                System.out.println("PUSH");
                //回应ACK
                ByteBuf byteBuf = datagramPacket.content().copy(0, 4);
                try {
                    byteBuf.setByte(3, PKT_PUSH_ACK);
                    channelHandlerContext.channel().writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(byteBuf), new InetSocketAddress(datagramPacket.sender().getHostString(), datagramPacket.sender().getPort())));

                    //去掉头部12字节
                    list.add(new String(datagramPacket.content().toString(CharsetUtil.UTF_8).substring(6)));
                } finally {
                    byteBuf.release();
                }
                break;

            case PKT_PULL_DATA:
                ByteBuf byteBuf1 = datagramPacket.content().copy(0, 4);
                try {
                    byteBuf1.setByte(3, PKT_PULL_ACK);
                    channelHandlerContext.channel().writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(byteBuf1), new InetSocketAddress(datagramPacket.sender().getHostString(), datagramPacket.sender().getPort())));
                    byte[] buffer = new byte[4];
                    //如果是堆缓冲区
                    if (datagramPacket.content().hasArray()) {
                        buffer = datagramPacket.content().array();
                    } else {
                        //如果是直接缓冲区
                        datagramPacket.content().getBytes(0, buffer);
                    }
                    DownInfoForm info = null;
                    byte[] down;
                    buffer[3] = PKT_PULL_RESP;

//                    for (int i = 0; i < 8; i++) {
                    info = (DownInfoForm) LoRaServer.queueDown.poll(1, TimeUnit.MICROSECONDS);
                    if (info == null) {
                        break;
//                        throw new Exception("info == null");
                    }
                    // 构造 JSON 数据
                    down = (ConstructJson.ToJsonStr(info)).getBytes();
                    byte[] phyDown = new byte[down.length + 4];
                    System.arraycopy(buffer, 0, phyDown, 0, 4);
                    System.arraycopy(down, 0, phyDown, 4, down.length);
                    LOGGER.info("pull from the queue and the queue size = {}", LoRaServer.queueDown.size());
                    channelHandlerContext.channel().writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(phyDown), new InetSocketAddress(datagramPacket.sender().getHostString(), datagramPacket.sender().getPort())));

//                    }
                    channelHandlerContext.flush();
                } finally {
                    byteBuf1.release();
                }
                break;
            case PKT_PULL_ACK:
                break;
            default:
//                System.out.println(", unexpected command");

        }


    }
}
