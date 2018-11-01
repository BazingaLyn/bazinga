package org.bazinga;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author liguolin
 * @create 2018-11-01 22:13
 **/
public class BazingaServerOutboundHandler2 extends ChannelOutboundHandlerAdapter {

    private Logger logger = LoggerFactory.getLogger(BazingaServerOutboundHandler2.class);

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        logger.info(">>>>>>>>>>>> BazingaServerOutboundHandler2 write invoke");
        super.write(ctx,msg,promise);

    }
}
