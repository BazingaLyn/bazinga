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
        if(msg instanceof Student){
            byte[] infos = JSON.toJSONString(msg).getBytes();
            out.writeByte((byte)1);
            out.writeInt(infos.length);

        }
    }
}
