package org.bazinga;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import org.bazinga.domain.Request;
import org.bazinga.domain.Response;
import org.bazinga.protocol.BazingaTransferProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author liguolin
 * @create 2018-10-24 10:55
 **/
public class BazingaSimpleCodec extends ByteToMessageCodec {

    private Logger logger = LoggerFactory.getLogger(BazingaSimpleCodec.class);


    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {

        logger.info("BazingaSimpleCodec encode invoke....");
        out.writeByte(msg instanceof Request ? (byte)1 : (byte)2);
        byte[] infos = JSON.toJSONString(msg).getBytes();
        out.writeInt(infos.length);
        out.writeBytes(infos);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List out) throws Exception {

        BazingaTransferProtocol bazingaTransferProtocol = new BazingaTransferProtocol();

        logger.info("BazingaSimpleCodec decode invoke....");
        if(in.readableBytes() < 1){
            return;
        }
        in.markReaderIndex();
        bazingaTransferProtocol.setType(in.readByte());
        if(in.readableBytes() < 4){
            in.resetReaderIndex();
            return;
        }
        bazingaTransferProtocol.setLength(in.readInt());
        if(in.readableBytes() < bazingaTransferProtocol.getLength()){
            in.resetReaderIndex();
            return;
        }

        byte[] contents = new byte[bazingaTransferProtocol.getLength()];
        in.readBytes(contents);

        if(bazingaTransferProtocol.getType() == (byte)1){
            Request request = JSON.parseObject(contents,Request.class);
            out.add(request);
        }else if(bazingaTransferProtocol.getType() == (byte)2){
            Response response = JSON.parseObject(contents,Response.class);
            out.add(response);
        }
    }
}
