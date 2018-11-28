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
 * @create 2018-11-28 11:13
 **/
public class BazingaUdpSenderHandler extends MessageToMessageEncoder<String> {


    //Reciever的端口
    private static int R_PORT = 2222;

    private InetSocketAddress remoteAddress = new InetSocketAddress("255.255.255.255", R_PORT);

    @Override
    protected void encode(ChannelHandlerContext ctx, String s, List<Object> out) throws Exception {
        byte[] bytes = s.getBytes(CharsetUtil.UTF_8);
        ByteBuf buf = ctx.alloc().buffer(bytes.length);
        buf.writeBytes(bytes);
        DatagramPacket packet = new DatagramPacket(buf, remoteAddress);
        out.add(packet);



    }
}
