package org.bazinga;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author liguolin
 * @create 2018-10-23 20:42
 **/
public class SimpleMsgToByteEncoder extends MessageToByteEncoder<String> {

    private static Logger logger = LoggerFactory.getLogger(SimpleMsgToByteEncoder.class);

    @Override
    protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf out) throws Exception {

        logger.info("SimpleMsgToByteEncoder invoke encode...");

        out.writeInt(msg.getBytes().length);
        out.writeBytes(msg.getBytes());
    }
}
