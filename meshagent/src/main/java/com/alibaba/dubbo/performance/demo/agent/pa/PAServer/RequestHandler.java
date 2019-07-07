package com.alibaba.dubbo.performance.demo.agent.pa.PAServer;


import com.alibaba.dubbo.performance.demo.agent.utils.Client;
import com.alibaba.dubbo.performance.demo.agent.utils.model.JsonUtils;
import com.alibaba.dubbo.performance.demo.agent.utils.model.Request;
import com.alibaba.dubbo.performance.demo.agent.utils.model.RpcInvocation;
import com.alibaba.dubbo.performance.demo.agent.pa.PAClient.ProviderAgentClient;
import com.alibaba.dubbo.performance.demo.agent.pa.PAClient.Clients;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
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
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        String s = msg.toString(Charset.defaultCharset());

        //temp variable
        long id = msg.readLong();
//        msg.skipBytes(136);
        String parameter = msg.slice().toString(Charset.defaultCharset());
//        long id = 0;
//        String parameter = null;
//        String[] strings = s.split("&");
//        if (strings[0].startsWith("id"))
//            id = Long.parseLong(strings[0].substring(strings[0].indexOf('=') + 1));
//        else
//            System.out.println("no id");
////        if (strings[1].startsWith("interface")) ;
////        else
////            System.out.println("no InterfaceName");
////        if (strings[2].startsWith("method")) ;
////        else
////            System.out.println("no method");
////        if (strings[3].startsWith("parameterTypesString")) ;
////        else
////            System.out.println("no parameterTypesString");
//        if (strings[4].startsWith("parameter"))
//            parameter = strings[4].substring(strings[4].indexOf('=') + 1);
//        else
//            System.out.println("no parameter");


        Request request = new Request(id);
        RpcInvocation invocation = new RpcInvocation();
        invocation.setMethodName("hash");
        invocation.setAttachment("path", "com.alibaba.dubbo.performance.demo.provider.IHelloService");
        invocation.setParameterTypes("Ljava/lang/String;");    // Dubbo内部用"Ljava/lang/String"来表示参数类型是String

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(out));
        JsonUtils.writeObject(parameter, writer);
        invocation.setArguments(out.toByteArray());

        request.setVersion("2.0.0");
        request.setTwoWay(true);
        request.setData(invocation);

        Client client = Clients.getClient();
        if (!client.isStart()) {
            synchronized (ProviderAgentClient.class) {
                if (!client.isStart()) {
                    client.start();
                }
            }
        }
        Channel channel = client.getChannel();
        channel.writeAndFlush(request);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("accept connection from consumer agent");

        Clients.setServer(ctx.channel());

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.warn("disconnect from consumer agent");
        super.channelInactive(ctx);
    }
}
