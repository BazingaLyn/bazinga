package org.bazinga.transport;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.bazinga.transport.decoder.RpcDecoder;
import org.bazinga.transport.encoder.RpcEncoder;
import org.bazinga.transport.handler.RpcServerHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author liguolin
 * @create 2018-11-20 17:47
 **/
public class DefaultRpcServer implements RpcServer {

    private Logger logger = LoggerFactory.getLogger(DefaultRpcServer.class);

    private int port;

    private ServerBootstrap serverBootstrap;
    private EventLoopGroup boss;
    private EventLoopGroup worker;

    private int workerNum = Runtime.getRuntime().availableProcessors() << 1;
    private int writeBufferLowWaterMark;
    private int writeBufferHighWaterMark;


    public DefaultRpcServer(int port){
        this.port = port;
    }


    @Override
    public void init() {

        serverBootstrap = new ServerBootstrap();
        boss = new NioEventLoopGroup(1);
        worker = new NioEventLoopGroup(workerNum);
        serverBootstrap.group(boss,worker);
        serverBootstrap.channel(NioServerSocketChannel.class);
    }

    @Override
    public void start() {
        serverBootstrap.localAddress(port);

        serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new RpcEncoder());
                ch.pipeline().addLast(new RpcDecoder());
                ch.pipeline().addLast(new RpcServerHandler());
            }
        });

        try {
            this.serverBootstrap.bind().sync();
        } catch (InterruptedException e) {
        }


    }
}
