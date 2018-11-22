package org.bazinga.transport.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.bazinga.rpc.DefaultRequest;

/**
 * @author liguolin
 * @create 2018-11-21 19:29
 **/
public class RpcServerHandler extends SimpleChannelInboundHandler<DefaultRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DefaultRequest msg) throws Exception {

    }
}
