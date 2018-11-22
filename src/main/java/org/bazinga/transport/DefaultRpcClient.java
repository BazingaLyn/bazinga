package org.bazinga.transport;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.bazinga.transport.decoder.RpcDecoder;
import org.bazinga.transport.encoder.RpcEncoder;
import org.bazinga.transport.handler.RpcClientHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liguolin
 * @create 2018-11-20 17:47
 **/
public class DefaultRpcClient implements RpcClient {


    private Map<String,Channel> channelRoutes = new ConcurrentHashMap<>(16);

    private Bootstrap bootstrap;

    private EventLoopGroup worker;

    private int nWorkers = 1;

    @Override
    public void init() {

        worker = new NioEventLoopGroup(nWorkers);
        bootstrap = new Bootstrap();
        bootstrap.group(worker);

    }

    @Override
    public Channel getAvailableChannel(String address) {

        Channel channel = channelRoutes.get(address);
        if(channel != null && channel.isActive()){
            return channel;
        }

        return doGetAvailableChannel(address);
    }

    private Channel doGetAvailableChannel(String address) {
        return null;
    }

    @Override
    public void start() {
        bootstrap.channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {

            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new RpcEncoder());
                ch.pipeline().addLast(new RpcDecoder());
                ch.pipeline().addLast(new RpcClientHandler());
            }
        });
    }
}
