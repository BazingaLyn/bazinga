package org.bazinga;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

/**
 * @author liguolin
 * @create 2018-11-28 9:46
 **/
public class BazingaUdpReceiver {


    //Reciever的端口
    private static int R_PORT = 2222;


    public static void main(String[] args) throws InterruptedException {

        NioEventLoopGroup group = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioDatagramChannel.class).option(ChannelOption.SO_BROADCAST,true)
                .handler(new ChannelInitializer<NioDatagramChannel>() {
                    @Override
                    protected void initChannel(NioDatagramChannel ch) throws Exception {
                        ch.pipeline().addLast(new BazingaUdpReceiverHandler());
                    }
                });
        try {
            //4.bind到指定端口，并返回一个channel，该端口就是监听UDP报文的端口
            Channel channel = bootstrap.bind(R_PORT).sync().channel();
            //5.等待channel的close
            channel.closeFuture().sync();
            //6.关闭group
            group.shutdownGracefully();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
