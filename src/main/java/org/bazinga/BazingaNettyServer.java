package org.bazinga;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author liguolin
 * @create 2018-10-18 20:40
 **/
public class BazingaNettyServer {



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
//                    socketChannel.pipeline().addLast(new HelloWorldServerHandler());
                }
            });

        }catch (Exception e){

        }


    }
}
