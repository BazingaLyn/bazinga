package org.bazinga;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import org.bazinga.domain.Response;
import org.bazinga.domain.ResponseFuture;
import org.bazinga.rpc.NettyRpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author liguolin
 * @create 2018-11-22 13:51:53
 **/
public class BazingaClientHandler extends SimpleChannelInboundHandler<Response> {

    private Logger logger = LoggerFactory.getLogger(BazingaClientHandler.class);

    private NettyRpcClient nettyRpcClient;

    public BazingaClientHandler(NettyRpcClient nettyRpcClient) {

        this.nettyRpcClient = nettyRpcClient;

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Response msg) throws Exception {

        logger.info("hello return invoke..... {}",msg);

        Map<Long, ResponseFuture> nettyRpcClientMaps = nettyRpcClient.getMaps();
        ResponseFuture responseFuture = nettyRpcClientMaps.get(msg.getId());
        responseFuture.putResponse(msg);

    }
}
