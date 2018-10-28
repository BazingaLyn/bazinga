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

import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * @author bazinga
 * @create 2018-10-19 11:04
 **/
public class BazingaNettyClient {

    public static void main(String[] args) {

        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        try{

            b.group(group)
                    .channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline p = ch.pipeline();
                    p.addLast(new StringDecoder());
                    p.addLast(new StringEncoder());
                    p.addLast(new BazingaClientHandler());
                }
            });
            SocketAddress socketAddress =  new InetSocketAddress("127.0.0.1", 8082);
            ChannelFuture future = b.connect(socketAddress).sync();
            future.channel().writeAndFlush("Hello Netty");
            future.channel().closeFuture().sync();
        }catch (Exception e){
            group.shutdownGracefully();
        }

    }
}
