package org.bazinga;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
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


    private Bootstrap bootstrap;

    public BazingaNettyClient(ChannelInboundHandler channelInboundHandler){
        init(channelInboundHandler);
    }

    public void init(ChannelInboundHandler channelInboundHandler){

        bootstrap = new Bootstrap().group(new NioEventLoopGroup()).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline p = ch.pipeline();
                p.addLast("bazingaSimpleCodec", new BazingaSimpleCodec());
                p.addLast(channelInboundHandler);

            }
        });
    }


    public Channel doCreateChannel(String address){
        ChannelFuture channelFuture = bootstrap.connect(string2SocketAddress(address));
        return channelFuture.channel();
    }


    public static SocketAddress string2SocketAddress(String addr) {
        String[] s = addr.split(":");
        InetSocketAddress isa = new InetSocketAddress(s[0], Integer.valueOf(s[1]));
        return isa;
    }









}
