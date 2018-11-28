package org.bazinga;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.util.concurrent.TimeUnit;

/**
 * @author liguolin
 * @create 2018-11-28 11:11
 **/
public class BazingaUdpSender {

    //Sender的端口
    private static int S_PORT = 1111;


    public static void main(String[] args) {
        //1.NioEventLoopGroup是执行者
        NioEventLoopGroup group = new NioEventLoopGroup();
        //2.启动器
        Bootstrap bootstrap = new Bootstrap();
        //3.配置启动器
        //3.1指定group
        bootstrap.group(group)
                //3.2指定channel
                .channel(NioDatagramChannel.class)
                //3.3指定为广播模式
                .option(ChannelOption.SO_BROADCAST,true)
                .handler(new ChannelInitializer<NioDatagramChannel>() {
                    @Override
                    protected void initChannel(NioDatagramChannel nioDatagramChannel) throws Exception {
                        //3.4在pipeline中加入编码器
                        nioDatagramChannel.pipeline().addLast(new BazingaUdpSenderHandler());
                    }
                });

        try {
            //4.bind并返回一个channel
            Channel channel = bootstrap.bind(S_PORT).sync().channel();
            for (int i = 0; i < 1000; i++) {
                //5.发送数据
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
