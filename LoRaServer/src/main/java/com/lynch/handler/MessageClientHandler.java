package com.lynch.handler;

import com.lynch.LoRaServer;
import com.lynch.domain.DownInfoForm;
import com.lynch.util.ConstructJson;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * Created by lynch on 2019-04-10. <br>
 * 与网关两个tcp连接时，服务器作为客户端以心跳机制向下发送数据
 **/
public class MessageClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
            throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.WRITER_IDLE) {
                if (LoRaServer.queueDown.size() != 0) {
                    DownInfoForm info = null;
                    try {
                        info = (DownInfoForm) LoRaServer.queueDown.poll(1, TimeUnit.MICROSECONDS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    byte[] head = new byte[]{0x02, 0x11, 0x22, 0x03};
                    String headStr = new String(head);
                    // 构造 JSON 数据
                    String down = (ConstructJson.ToJsonStr(info));
                    ctx.writeAndFlush(Unpooled.copiedBuffer(headStr + down, CharsetUtil.UTF_8));
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }


}
