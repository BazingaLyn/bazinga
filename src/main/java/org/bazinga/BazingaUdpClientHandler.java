package org.bazinga;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.CharsetUtil;

import java.util.List;

/**
 * @author liguolin
 * @create 2018-11-28 18:37
 **/
public class BazingaUdpClientHandler extends MessageToMessageDecoder<DatagramPacket> {


    @Override
    protected void decode(ChannelHandlerContext ctx, DatagramPacket datagramPacket, List<Object> out) throws Exception {
        ByteBuf buf = datagramPacket.content();
        String msg = buf.toString(CharsetUtil.UTF_8);
        System.out.println("Sender receive: "+ msg);
    }
}
