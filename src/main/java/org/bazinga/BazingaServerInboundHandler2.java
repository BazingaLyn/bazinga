package org.bazinga;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author bazinga
 * @create 2018-10-18 21:08
 **/
public class BazingaServerInboundHandler2 extends ChannelInboundHandlerAdapter {


    private Logger logger = LoggerFactory.getLogger(BazingaServerInboundHandler2.class);


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info(">>>>>>>>>>>> BazingaServerInboundHandler2 channelActive");
        super.channelActive(ctx);
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("invoke BazingaServerInboundHandler2 from remote ip {} receive msg {}",ctx.channel().remoteAddress().toString(),msg);
        super.channelRead(ctx,msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error(">>>>>>>>>>> BazingaServerInboundHandler2 exceptionCaught ");
        super.exceptionCaught(ctx,cause);
    }
}
