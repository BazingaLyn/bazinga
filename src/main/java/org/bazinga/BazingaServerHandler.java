package org.bazinga;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author liguolin
 * @create 2018-10-18 21:08
 **/
public class BazingaServerHandler extends SimpleChannelInboundHandler<String> {


    private Logger logger = LoggerFactory.getLogger(BazingaServerHandler.class);


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
        logger.info("channelRead0 read msg {}",msg);
        channelHandlerContext.channel().writeAndFlush(msg);
    }
}
