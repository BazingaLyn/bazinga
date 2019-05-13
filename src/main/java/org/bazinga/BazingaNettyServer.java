package org.bazinga;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author liguolin
 * @create 2018-10-18 20:40
 **/
public class BazingaNettyServer {

    private final static Logger logger = LoggerFactory.getLogger(BazingaNettyServer.class);


    public static void main(String[] args) {

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try{
            ServerBootstrap sbs = new ServerBootstrap();
            sbs.group(bossGroup,workerGroup);
            sbs.channel(NioServerSocketChannel.class);
            sbs.handler(new BazingaChannelHandler());
            sbs.childHandler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast("decoder", new StringDecoder());
                    socketChannel.pipeline().addLast("encoder", new StringEncoder());
                    socketChannel.pipeline().addLast(new BazingaServerHandler());
                }
            });
            sbs.bind("127.0.0.1",8082).sync();
            logger.info(">>>>>>>> BazingaNettyServer start success");
        }catch (Exception e){
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
