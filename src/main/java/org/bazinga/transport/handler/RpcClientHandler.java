package org.bazinga.transport.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.bazinga.rpc.DefaultRequest;
import org.bazinga.rpc.DefaultResponse;

/**
 * @author liguolin
 * @create 2018-11-21 19:29
 **/
public class RpcClientHandler extends SimpleChannelInboundHandler<DefaultResponse> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DefaultResponse msg) throws Exception {

    }
}
