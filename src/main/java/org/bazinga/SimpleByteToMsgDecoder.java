package org.bazinga;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author liguolin
 * @create 2018-10-23 20:47
 **/
public class SimpleByteToMsgDecoder extends ReplayingDecoder<BazingaDecoderState> {

    private static Logger logger = LoggerFactory.getLogger(SimpleByteToMsgDecoder.class);

    private int length;

    private int count = 0;


    /**
     * Creates a new instance with the specified initial state.
     */
    public SimpleByteToMsgDecoder() {
        super(BazingaDecoderState.READ_LENGTH);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        logger.info("SimpleByteToMsgDecoder invoke decode and invoke time is {}",++count);

        switch (state()) {
            case READ_LENGTH:
                length = in.readInt();
                checkpoint(BazingaDecoderState.READ_CONTENT);
            case READ_CONTENT:
                byte[] contents = new byte[length];
                in.readBytes(contents);
                checkpoint(BazingaDecoderState.READ_LENGTH);
                out.add(new String(contents));
                break;
            default:
                logger.error("Shouldn't reach here.");
        }
    }

//    @Override
//    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
//
//        logger.info("SimpleByteToMsgDecoder invoke decode and invoke time is {}",++count);
//
//        byte[] bytes = new byte[in.readInt()];
//        in.readBytes(bytes);
//
//        out.add(new String(bytes));
//    }
}
