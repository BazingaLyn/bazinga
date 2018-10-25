package org.bazinga;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * @author liguolin
 * @create 2018-10-19 11:04
 **/
public class BazingaNettyClient {

    private static Logger logger = LoggerFactory.getLogger(BazingaNettyClient.class);

    private static BazingaSharableHandler bazingaSharableHandler = new BazingaSharableHandler();

    public static void main(String[] args) {

        for(int i = 0;i < 2;i++){
            logger.info("hello");
            connection("127.0.0.1",8082);
        }
    }


    public static void connection(String host,int port){
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap boot = new Bootstrap();
        try{

            boot.group(group)
                    .channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline p = ch.pipeline();
                    p.addLast(new StringDecoder());
                    p.addLast(new StringEncoder());
                    p.addLast(bazingaSharableHandler);
                    p.addLast(new BazingaClientHandler());
                }
            });

            WriteBufferWaterMark waterMark = new WriteBufferWaterMark(300, 400);
            boot.option(ChannelOption.WRITE_BUFFER_WATER_MARK, waterMark);

            SocketAddress socketAddress =  new InetSocketAddress(host, port);
            ChannelFuture future = boot.connect(socketAddress).sync();
            Channel channel = future.channel();
            channel.writeAndFlush("hello world...");
        }catch (Exception e){
            group.shutdownGracefully();
        }
    }
}
