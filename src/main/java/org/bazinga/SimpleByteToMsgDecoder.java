package org.bazinga;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author liguolin
 * @create 2018-10-23 20:47
 **/
public class SimpleByteToMsgDecoder extends ByteToMessageDecoder {

    private static Logger logger = LoggerFactory.getLogger(SimpleByteToMsgDecoder.class);


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        logger.info("SimpleByteToMsgDecoder invoke decode");

        if(in.readableBytes() < 4){
            return;
        }

        in.markReaderIndex();
        int contextLength = in.readInt();

        if(in.readableBytes() < contextLength){
            in.resetReaderIndex();
            return;
        }

        byte[] byteArray = new byte[contextLength];
        in.readBytes(byteArray);

        out.add(new String(byteArray));
    }
}
