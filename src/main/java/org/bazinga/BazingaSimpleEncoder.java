package org.bazinga;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.bazinga.domain.Student;

/**
 * @author liguolin
 * @create 2018-10-24 10:41
 **/
public class BazingaSimpleEncoder extends MessageToByteEncoder<Object> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        out.writeByte(msg instanceof Student ? (byte)1 : (byte)2);
        byte[] infos = JSON.toJSONString(msg).getBytes();
        out.writeInt(infos.length);
        out.writeBytes(infos);
    }
}
