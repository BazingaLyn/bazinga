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

    private final static Logger logger = LoggerFactory.getLogger(BazingaNettyClient.class);


    public static void main(String[] args) {

        logger.info("ready connect to server");

        Channel channel = null;

        NettyConnector nettyConnector = new NettyConnector();
        try {
            nettyConnector.connect("127.0.0.1",8082);
        } catch (Exception e) {
        }


    }

}
