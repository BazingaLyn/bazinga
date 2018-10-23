package org.bazinga;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.ReplayingDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author liguolin
 * @create 2018-10-23 20:47
 **/
public class SimpleByteToMsgDecoder extends ReplayingDecoder<Void> {

    private static Logger logger = LoggerFactory.getLogger(SimpleByteToMsgDecoder.class);


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        logger.info("SimpleByteToMsgDecoder invoke decode");

        byte[] bytes = new byte[in.readInt()];
        in.readBytes(bytes);

        out.add(new String(bytes));
    }
}
