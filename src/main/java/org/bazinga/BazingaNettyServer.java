package org.bazinga;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author liguolin
 * @create 2018-10-18 20:40
 **/
public class BazingaNettyServer {

    private final static Logger logger = LoggerFactory.getLogger(BazingaNettyServer.class);

    private static int default_fixed_length = "hello world my name is lyncc".getBytes().length;


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
            sbs.channel(NioServerSocketChannel.class).
            childHandler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    ChannelPipeline pipeline = socketChannel.pipeline();
                    pipeline.addLast("bazingaSimpleCodec", new BazingaSimpleCodec());
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
