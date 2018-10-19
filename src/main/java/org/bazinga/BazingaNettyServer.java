package org.bazinga;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author liguolin
 * @create 2018-10-18 20:40
 **/
public class BazingaNettyServer {

    private final static Logger logger = LoggerFactory.getLogger(BazingaNettyServer.class);

    static final String HOST = "127.0.0.1";

    static final int PORT = 8082;



    public static void main(String[] args) {

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try{
            ServerBootstrap sbs = new ServerBootstrap();
            sbs.group(bossGroup,workerGroup);
            sbs.channel(NioServerSocketChannel.class);
            sbs.childHandler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast("decoder", new StringDecoder());
                    socketChannel.pipeline().addLast("encoder", new StringEncoder());
                    socketChannel.pipeline().addLast(new BazingaServerHandler());
                }
            });
            sbs.bind(HOST,PORT).sync().addListener(future -> {

                if(future.isSuccess()){
                    logger.info(">>>>>>>>>>>BazingaNettyServer bind {}:{} successfully",HOST,PORT);
                }else{
                    logger.error(">>>>>>>>>>>BazingaNettyServer bind {}:{} fail need reconnect",HOST,PORT);
                }

            });
        }catch (Exception e){
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }


    }
}
