package com.bowen.middleware.pa.PAClient;

import com.bowen.middleware.utils.model.Bytes;
import com.bowen.middleware.utils.model.JsonUtils;
import com.bowen.middleware.utils.model.Request;
import com.bowen.middleware.utils.model.RpcInvocation;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class DubboRpcEncoder extends MessageToByteEncoder {
    private static final int HEADER_LENGTH = 16;
    private static final short MAGIC = (short) 0xdabb;
    private static final byte FLAG_REQUEST = (byte) 0x80;
    private static final byte FLAG_TWOWAY = (byte) 0x40;
    private static final byte FLAG_EVENT = (byte) 0x20;

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf buffer)throws Exception {
        Request req = (Request)msg;

        byte[] header = new byte[HEADER_LENGTH];
        Bytes.short2bytes(MAGIC,header);

        header[2] = (byte)(FLAG_REQUEST | 6);
        if(req.isTwoWay()) header[2] |= FLAG_TWOWAY;
        if(req.isEvent()) header[2] |= FLAG_EVENT;

        Bytes.long2bytes(req.getId(),header,4);

        int savedWriterIndex = buffer.writerIndex();
        buffer.writerIndex(savedWriterIndex + HEADER_LENGTH);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        encodeRequestData(bos,req.getData());
    }

    private void encodeRequestData(OutputStream out, Object data)throws Exception{
        RpcInvocation inv = (RpcInvocation) data;
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(out));

        JsonUtils.writeObject(inv.getAttachment("dubbo","2.0.1"),writer);
        JsonUtils.writeObject(inv.getAttachment("path"),writer);
        JsonUtils.writeObject(inv.getAttachment("version"),writer);
        JsonUtils.writeObject(inv.getMethodName(),writer);
        JsonUtils.writeObject(inv.getParameterTypes(),writer);

        JsonUtils.writeBytes(inv.getArguments(),writer);
        JsonUtils.writeObject(inv.getArguments(),writer);
    }
}
