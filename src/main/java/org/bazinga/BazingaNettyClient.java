package org.bazinga;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * @author liguolin
 * @create 2018-10-19 11:04
 **/
public class BazingaNettyClient {

    private final static Logger logger = LoggerFactory.getLogger(BazingaNettyClient.class);

    static final String HOST = "127.0.0.1";

    static final int PORT = 8082;

    public static void main(String[] args) {
        connect();
    }

    private static void connect() {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        try{
            b.group(group)
                    .channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline p = ch.pipeline();
                    p.addLast("bazingaSimpleCodec", new BazingaSimpleCodec());
                    p.addLast(new BazingaClientHandler());
                }
            });
            SocketAddress socketAddress =  new InetSocketAddress(HOST, PORT);
            b.connect(socketAddress).sync().addListener(future -> {
                if(future.isSuccess()){
                    logger.info(">>>>>>>>>>>BazingaNettyClient connect {}:{} successfully",HOST,PORT);
                }else{
                    logger.error(">>>>>>>>>>>BazingaNettyClient connect {}:{} fail need reconnect",HOST,PORT);
                }
            });
        }catch (Exception e){
            group.shutdownGracefully();
        }
    }
}
