package com.bowen.middleware.pa.PAServer;

import com.bowen.middleware.pa.PAClient.Clients;
import com.bowen.middleware.pa.PAClient.ProviderAgentClient;
import com.bowen.middleware.utils.Client;
import com.bowen.middleware.utils.model.JsonUtils;
import com.bowen.middleware.utils.model.Request;
import com.bowen.middleware.utils.model.RpcInvocation;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;

public class RequestHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg)throws Exception {
        String s = msg.toString(Charset.defaultCharset());
        long id = msg.readLong();
        String parameter = msg.slice().toString(Charset.defaultCharset());
        Request request = new Request(id);
        RpcInvocation invocation = new RpcInvocation();
        invocation.setMethodName("hash");
        invocation.setAttachment("path"," ");
        invocation.setParameterTypes("Ljava/lang/String");//Dubbo内部用"Ljava/lang/String"来表示参数类型是String

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(out));
        JsonUtils.writeObject(parameter,writer);
        invocation.setArguments(out.toByteArray());

        request.setVersion("2.0.0");
        request.setTwoWay(true);
        request.setData(invocation);

        Client client = Clients.getClient();
        if(!client.isStart()){
            synchronized (ProviderAgentClient.class){
                if(!client.isStart()){
                    client.start();
                }
            }
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx)throws Exception{
        logger.info("accept connection from consumer agent");
        Clients.setServer(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.warn("disconnect from consumer agent");
        super.channelInactive(ctx);
    }
}
