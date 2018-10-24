package org.bazinga;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * @author liguolin
 * @create 2018-10-24 16:05
 **/
public class NettyConnector {

    private Logger logger = LoggerFactory.getLogger(NettyConnector.class);

    // 读超时
    private static final int READ_IDEL_TIME_OUT         = 0;
    // 写超时
    private static final int WRITE_IDEL_TIME_OUT        = 4;
    // 所有超时
    private static final int ALL_IDEL_TIME_OUT          = 0;

    public void connect(final String host,final int port) {

        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();

        ChannelFuture future = null;
        try{

            b.group(group)
                    .channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline p = ch.pipeline();
                    p.addLast(new IdleStateHandler(READ_IDEL_TIME_OUT,
                            WRITE_IDEL_TIME_OUT, ALL_IDEL_TIME_OUT, TimeUnit.SECONDS));
                    p.addLast(new StringDecoder());
                    p.addLast(new StringEncoder());
                    p.addLast(new BazingaClientHandler(NettyConnector.this,host,port));
                }
            });
            SocketAddress socketAddress =  new InetSocketAddress(host, port);
            future = b.connect(socketAddress).sync();
            logger.info("connect successfully.....");
        }catch (Exception e){
            logger.info("connection exception.....",e);
            if (null != future) {
                if (future.channel() != null && future.channel().isOpen()) {
                    future.channel().close();
                }
            }
            logger.info("准备重连");
            connect(host, port);
        }
    }
}
