package org.bazinga;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author liguolin
 * @create 2018-11-28 9:53
 **/
public class BazingaUdpReceiverHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    private static final Logger logger = LoggerFactory.getLogger(BazingaUdpReceiverHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
        logger.info("开始接收来自client的数据");
        final ByteBuf buf = msg.content();
        String _msg = buf.toString(CharsetUtil.UTF_8);
        logger.info("BazingaUdpReceiver {}",_msg);

    }
}
