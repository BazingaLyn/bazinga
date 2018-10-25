package org.bazinga;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import org.bazinga.domain.Url;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author liguolin
 * @create 2018-10-19 11:06
 **/
public class BazingaClientHandler extends ChannelInboundHandlerAdapter {

    private Logger logger = LoggerFactory.getLogger(BazingaClientHandler.class);

    private String HEART_MSG = "heartbeats";

    private int sample_count = 0;

    private ReconnectManager reconnectManager;

    public BazingaClientHandler(ReconnectManager reconnectManager) {
        this.reconnectManager = reconnectManager;
    }

    /**
     *
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.WRITER_IDLE) {
                logger.info(">>>>> 我们已经4秒没有发送数据给服务器了，为了防止服务器以为我们死了，我们需要发送一个心跳数据");
                ++sample_count;
                if(sample_count < 4){
                    ctx.pipeline().writeAndFlush(HEART_MSG);
                }else{
                    logger.info(">>>> 模拟假死 不再发送心跳");
                }

            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info(">>>>>> BazingaClientHandler channelActive");
        ctx.pipeline().writeAndFlush("hello world");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info(">>>>>> BazingaClientHandler channelInactive");
        logger.info(">>>>>>>> channelInactive ready reconnect 在channelInactive时候准备重连");

        Attribute<Url> attribute = ctx.channel().attr(AttributeKey.valueOf("heartbeatCount"));
        Url url = attribute.get();
        if(url != null){
            logger.info(">>>>>> BazingaClientHandler 准备重连的URL is {}",url);
            reconnectManager.addReconnectTask(url);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info(">>>>>> receive msg  {} from server",msg);
    }
}
