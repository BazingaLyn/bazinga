package org.bazinga;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author liguolin
 * @create 2018-10-19 11:06
 **/
public class BazingaClientHandler extends ChannelInboundHandlerAdapter {

    private Logger logger = LoggerFactory.getLogger(BazingaClientHandler.class);



    private String too_long_context = "TCP将保持它首部和数据的检验和。这是一个端到端的检验和，目的是检测数据在传输过程中的任何变化。如果收到段的检验和有差错，TCP将丢弃这个报文段和不确认收到此报文段。 (校验出包有错，丢弃报文段，不给出响应，TCP发送数据端，超时时会重发数据)\n" +
            "5、既然TCP报文段作为IP数据报来传输，而IP数据报的到达可能会失序，因此TCP报文段的到达也可能会失序。如果必要，TCP将对收到的数据进行重新排序，将收到的数据以正确的顺序交给应用层。 (对失序数据进行重新排序，然后才交给应用层)\n" +
            "6、既然IP数据报会发生重复，TCP的接收端必须丢弃重复的数据。(对于重复数据，能够丢弃重复数据)\n" +
            "7、TCP还能提供流量控制。TCP连接的每一方都有固定大小的缓冲空间。TCP的接收端只允许另一端发送接收端缓冲区所能接纳的数据。这将防止较快主机致使较慢主机的缓冲区溢出。(TCP可以进行流量控制，防止较快主机致使较慢主机的缓冲区溢出)TCP使用的流量控制协议是可变大小的滑动窗口协议。\n" +
            "字节流服务::两个应用程序通过TCP连接交换8bit字节构成的字节流。TCP不在字节流中插入记录标识符。我们将这称为字节流服务（bytestreamservice）。\n" +
            "TCP对字节流的内容不作任何解释:: TCP对字节流的内容不作任何解释。TCP不知道传输的数据字节流是二进制数据，还是ASCII字符、EBCDIC字符或者其他类型数据。对字节流的解释由TCP连接双方的应用层解释。\n" +
            "--------------------- \n" +
            "作者：北方的忍冬草 \n" +
            "来源：CSDN \n" +
            "原文：https://blog.csdn.net/dccmxj/article/details/52103800 \n" +
            "版权声明：本文为博主原创文章，转载请附上博文链接！";



    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info(">>>>>> BazingaClientHandler channelActive");

        // 粘包
        for(int i = 0; i< 100 ;i++){
            ctx.pipeline().writeAndFlush("hello world my name is lyncc");
        }

        // 拆包
        ctx.pipeline().writeAndFlush(too_long_context);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info(">>>>>> receive msg  {} from server",msg);
    }
}
