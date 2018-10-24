package org.bazinga;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import org.bazinga.domain.Student;
import org.bazinga.domain.Teacher;
import org.bazinga.protocol.BazingaTransferProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author liguolin
 * @create 2018-10-24 10:55
 **/
public class BazingaSimpleDecoder extends ReplayingDecoder<BazingaSimpleState> {

    private Logger logger = LoggerFactory.getLogger(BazingaSimpleDecoder.class);


    public BazingaSimpleDecoder(){
        super(BazingaSimpleState.TYPE);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        logger.info("BazingaSimpleDecoder invoke decode....");
        BazingaTransferProtocol bazingaTransferProtocol = new BazingaTransferProtocol();

        switch (state()){
            case TYPE:
                bazingaTransferProtocol.setType(in.readByte());
                checkpoint(BazingaSimpleState.LENGTH);
            case LENGTH:
                bazingaTransferProtocol.setLength(in.readInt());
                checkpoint(BazingaSimpleState.CONTENT);
            case CONTENT:
                byte[] contents = new byte[bazingaTransferProtocol.getLength()];
                in.readBytes(contents);
                checkpoint(BazingaSimpleState.TYPE);
                if(bazingaTransferProtocol.getType() == (byte)1){
                    Student student = JSON.parseObject(contents, Student.class);
                    out.add(student);
                }else if(bazingaTransferProtocol.getType() == (byte)2){
                    Teacher teacher = JSON.parseObject(contents, Teacher.class);
                    out.add(teacher);
                }
                break;
            default:
                logger.error("should't reach here");
        }

    }
}
