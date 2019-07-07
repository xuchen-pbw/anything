package com.alibaba.dubbo.performance.demo.agent.ca.CAServer;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

public class ConsumerServerPipelineInitializer extends ChannelInitializer<Channel> {

    public ConsumerServerPipelineInitializer() {
    }

    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast("httpcodec", new HttpServerCodec());
        pipeline.addLast("httpaggregator", new HttpObjectAggregator(4096));
        pipeline.addLast("requestHandler", new RequestHandler());

    }
}
