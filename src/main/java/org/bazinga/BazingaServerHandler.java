package org.bazinga;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author liguolin
 * @create 2018-10-18 21:08
 **/
public class BazingaServerHandler extends ChannelInboundHandlerAdapter {


    private Logger logger = LoggerFactory.getLogger(BazingaServerHandler.class);

    private int count = 0;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("from remote ip {} receive msg {} receive count is {}",ctx.channel().remoteAddress().toString(),msg,++count);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error(">>>>>>>>>>> exceptionCaught ",cause);
    }
}
