package com.bowen.middleware.pa.PAClient;

import com.bowen.middleware.utils.model.Bytes;
import com.bowen.middleware.utils.model.RpcResponse;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.Arrays;
import java.util.List;

public class DubboRpcDecoder extends ByteToMessageDecoder {
    protected static final int HEADER_LENGTH = 16;
    protected static final byte FLAG_EVENT = (byte) 0x20;

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) {
        try{
            do {
                int saveReaderIndex = byteBuf.readerIndex();
                Object msg = null;
                try{
                    msg = decode2(byteBuf);
                }catch (Exception e){
                    throw e;
                }
                if(msg==DecodeResult.NEED_MORE_INPUT){
                    byteBuf.readerIndex(saveReaderIndex);
                    break;
                }

                list.add(msg);
            }while (byteBuf.isReadable());
        }finally {
            if (byteBuf.isReadable()){
                byteBuf.discardReadBytes();
            }
        }
    }

    enum DecodeResult{
        NEED_MORE_INPUT, SKIP_INPUT
    }

    private Object decode2(ByteBuf byteBuf){
        int saveReaderIndex = byteBuf.readerIndex();
        int readable = byteBuf.readableBytes();

        if(readable < HEADER_LENGTH){
            return DecodeResult.NEED_MORE_INPUT;
        }

        byte[] header = new byte[HEADER_LENGTH];
        byteBuf.readBytes(header);
        byte[] dataLen = Arrays.copyOfRange(header,12,16);
        int len = Bytes.bytes2int(dataLen);
        int tt = len + HEADER_LENGTH;
        if(readable<tt){
            return DecodeResult.NEED_MORE_INPUT;
        }

        byteBuf.readerIndex(saveReaderIndex);
        byte[] data = new byte[tt];
        byteBuf.readBytes(data);

        byte[] subArray = Arrays.copyOfRange(data,HEADER_LENGTH + 2,data.length -1);
        String s = new String(subArray);
        byte[] requestIdBytes = Arrays.copyOfRange(data,4,12);
        long requestId = Bytes.bytes2long(requestIdBytes,0);

        RpcResponse response = new RpcResponse();
        response.setRequestId(String.valueOf(requestId));
        response.setBytes(subArray);
        return response;
    }
}
