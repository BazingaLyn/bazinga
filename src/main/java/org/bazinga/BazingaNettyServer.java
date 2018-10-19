package org.bazinga;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author liguolin
 * @create 2018-10-18 20:40
 **/
public class BazingaNettyServer {

    private final static Logger logger = LoggerFactory.getLogger(BazingaNettyServer.class);


    // 读超时
    private static final int READ_IDEL_TIME_OUT         = 5;
    // 写超时
    private static final int WRITE_IDEL_TIME_OUT        = 0;
    // 所有超时
    private static final int ALL_IDEL_TIME_OUT          = 0;

    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 8082;
        bind(host,port);
    }

    private static void bind(String host,int port) {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap sbs = new ServerBootstrap();
            sbs.group(bossGroup, workerGroup);
            sbs.channel(NioServerSocketChannel.class);
            sbs.childHandler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    ChannelPipeline pipeline = socketChannel.pipeline();
                    pipeline.addLast(new IdleStateHandler(READ_IDEL_TIME_OUT,
                            WRITE_IDEL_TIME_OUT, ALL_IDEL_TIME_OUT, TimeUnit.SECONDS));
                    pipeline.addLast("decoder", new StringDecoder());
                    pipeline.addLast("encoder", new StringEncoder());
                    pipeline.addLast(new BazingaServerHandler());
                }
            });
            sbs.bind(host, port).sync().addListener(future -> {
                if (future.isSuccess()) {
                    logger.info(">>>>>>>>>>>BazingaNettyServer bind {}:{} successfully", host, port);
                } else {
                    logger.error(">>>>>>>>>>>BazingaNettyServer bind {}:{} fail need reconnect", host, port);
                }

            });
        } catch (Exception e) {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
