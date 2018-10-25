package org.bazinga;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.AttributeKey;
import org.bazinga.domain.Url;
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

    private ReconnectManager reconnectManager = new ReconnectManager(this);

    public void connect(final String host, final int port) throws Exception {

        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();

        ChannelFuture future = null;
        b.group(group)
                .channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline p = ch.pipeline();
                p.addLast(new IdleStateHandler(READ_IDEL_TIME_OUT,
                        WRITE_IDEL_TIME_OUT, ALL_IDEL_TIME_OUT, TimeUnit.SECONDS));
                p.addLast(new StringDecoder());
                p.addLast(new StringEncoder());
                p.addLast(new BazingaClientHandler(reconnectManager));
            }
        });
        SocketAddress socketAddress =  new InetSocketAddress(host, port);
        future = b.connect(socketAddress);

        future.awaitUninterruptibly();
        if (!future.isDone()) {
            String errMsg = "Create connection to " + host +":"+ port + " timeout!";
            logger.warn(errMsg);
            throw new Exception(errMsg);
        }
        if (future.isCancelled()) {
            String errMsg = "Create connection to " + host +":"+ port + " cancelled by user!";
            logger.warn(errMsg);
            throw new Exception(errMsg);
        }
        if (!future.isSuccess()) {
            String errMsg = "Create connection to " + host +":"+ port + " error!";
            logger.warn(errMsg);
            throw new Exception(errMsg, future.cause());
        }
        Channel channel =  future.channel();
        channel.attr(AttributeKey.valueOf("heartbeatCount")).setIfAbsent(new Url(host,port));
    }
}
