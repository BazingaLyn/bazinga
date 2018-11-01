package org.bazinga;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author bazinga
 * @create 2018-10-18 21:08
 **/
public class BazingaServerInboundHandler1 extends ChannelInboundHandlerAdapter {


    private Logger logger = LoggerFactory.getLogger(BazingaServerInboundHandler1.class);


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info(">>>>>>>>>>>> BazingaServerInboundHandler1 channelActive");
        super.channelActive(ctx);
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("invoke BazingaServerInboundHandler1 from remote ip {} receive msg {}",ctx.channel().remoteAddress().toString(),msg);
        super.channelRead(ctx,msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error(">>>>>>>>>>> BazingaServerInboundHandler1 ",cause);
        super.exceptionCaught(ctx,cause);
    }
}
