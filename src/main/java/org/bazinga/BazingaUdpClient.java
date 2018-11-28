package org.bazinga;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

/**
 * @author liguolin
 * @create 2018-11-28 11:11
 **/
public class BazingaUdpClient {

    private static int S_PORT = 1111;
    private static int R_PORT = 2222;




    public static void main(String[] args) {
        NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST,true)
                .handler(new ChannelInitializer<NioDatagramChannel>() {
                    @Override
                    protected void initChannel(NioDatagramChannel nioDatagramChannel) throws Exception {
                        nioDatagramChannel.pipeline().addLast(new BazingaUdpEncoder(R_PORT));
                        nioDatagramChannel.pipeline().addLast(new BazingaUdpClientHandler());
                    }
                });

        try {
            Channel channel = bootstrap.bind(S_PORT).sync().channel();
            for (int i = 0; i < 1000; i++) {
                channel.writeAndFlush("Send msg :" + i);
                System.out.println("Send msg :" + i);
            }

            //6.等待channel的close
            channel.closeFuture().sync();
            //7.关闭group
            group.shutdownGracefully();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
