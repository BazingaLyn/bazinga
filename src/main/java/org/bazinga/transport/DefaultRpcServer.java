package org.bazinga.transport;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;

/**
 * @author liguolin
 * @create 2018-11-20 17:47
 **/
public class DefaultRpcServer implements RpcServer {

    private int port;

    private ServerBootstrap serverBootstrap;
    private EventLoopGroup boss;
    private EventLoopGroup worker;

    private int workerNum;
    private int writeBufferLowWaterMark;
    private int writeBufferHighWaterMark;


    public DefaultRpcServer(int port){
        this.port = port;

    }


    @Override
    public void init() {

    }

    @Override
    public void start() {

    }
}
