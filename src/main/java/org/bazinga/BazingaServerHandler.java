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

    private String HEART_MSG = "heartbeats";

    private int loss_connect_time = 0;


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                ++loss_connect_time;
                logger.info("5秒没有接收到客户端的信息了,这是第{}次没有收到心跳消息了",loss_connect_time);
                if(loss_connect_time > 2){
                    logger.info("准备关闭这个可能已经断掉的channel");
                    ctx.channel().close();
                }

            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info(">>>>>>>>>>>> BazingaServerHandler channelActive");
        super.channelActive(ctx);
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("from remote ip {} receive msg {}",ctx.channel().remoteAddress().toString(),msg);
        if(msg instanceof String){
            String _msg = (String)msg;
            if(HEART_MSG.equals(_msg)){
                loss_connect_time = 0;
                logger.info("心跳数据不需要回复");
                return;
            }
        }
        ctx.pipeline().writeAndFlush(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error(">>>>>>>>>>> exceptionCaught ",cause);
    }
}
