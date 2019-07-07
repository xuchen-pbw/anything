package com.alibaba.dubbo.performance.demo.agent.pa.PAClient;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;

public class ProviderClientPipelineInitializer extends ChannelInitializer<Channel> {

    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast("encoder", new DubboRpcEncoder());
        pipeline.addLast("decoder", new DubboRpcDecoder());
        pipeline.addLast("handler", new ResponseHandler());
    }
}
