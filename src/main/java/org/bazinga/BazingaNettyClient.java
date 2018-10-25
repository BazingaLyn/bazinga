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

    public static void main(String[] args) {

        int sendInfo = "hello world...".getBytes().length;

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
                    p.addLast(new BazingaClientHandler());
                }
            });

            WriteBufferWaterMark waterMark = new WriteBufferWaterMark(300, 400);
            boot.option(ChannelOption.WRITE_BUFFER_WATER_MARK, waterMark);

            SocketAddress socketAddress =  new InetSocketAddress("127.0.0.1", 8082);
            ChannelFuture future = boot.connect(socketAddress).sync();
            Channel channel = future.channel();
            channel.writeAndFlush("hello world...");
            int totalHasSendInfos = 0;

//            for(int i = 0;i <100;i++){
//
//                if(totalHasSendInfos == 112){
//                    System.out.println("hello world");
//                }
//                if(channel.isWritable()){
//                    totalHasSendInfos +=sendInfo;
//
//                    logger.info("can write and flush and has sent {} bytes",totalHasSendInfos);
//                    channel.write("hello world...");
//                }else{
//                    logger.info("channel can't be writable");
//                }
//            }
//            channel.flush();

            future.channel().closeFuture().sync();
        }catch (Exception e){
            group.shutdownGracefully();
        }

    }
}
