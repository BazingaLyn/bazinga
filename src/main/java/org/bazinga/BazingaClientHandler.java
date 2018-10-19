package org.bazinga;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author liguolin
 * @create 2018-10-19 11:06
 **/
public class BazingaClientHandler extends ChannelInboundHandlerAdapter {

    private Logger logger = LoggerFactory.getLogger(BazingaClientHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info(">>>>>> BazingaClientHandler channelActive");
        ctx.pipeline().writeAndFlush("hello world");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info(">>>>>> receive msg  {} from server",msg);
    }
}
