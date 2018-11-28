package org.bazinga;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author liguolin
 * @create 2018-11-28 18:45
 **/
public class BazingaUdpEncoder extends MessageToMessageEncoder<String> {


    InetSocketAddress remoteAddress =null;

    public BazingaUdpEncoder(int boardcastPort){
        remoteAddress = new InetSocketAddress("255.255.255.255", boardcastPort);
    }


    @Override
    protected void encode(ChannelHandlerContext ctx, String s, List<Object> out) throws Exception {

        byte[] bytes = s.getBytes(CharsetUtil.UTF_8);
        ByteBuf buf = ctx.alloc().buffer(bytes.length);
        buf.writeBytes(bytes);
        DatagramPacket packet = new DatagramPacket(buf, remoteAddress);
        out.add(packet);
    }
}
