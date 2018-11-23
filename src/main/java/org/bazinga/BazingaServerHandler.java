package org.bazinga;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.bazinga.domain.Request;
import org.bazinga.domain.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author liguolin
 * 2018-11-22 13:49:33
 */
@ChannelHandler.Sharable
public class BazingaServerHandler extends SimpleChannelInboundHandler<Request> {

    private Logger logger = LoggerFactory.getLogger(BazingaServerHandler.class);


    private Map<String,Object> serviceContainer = new ConcurrentHashMap<>();

    public void publishLocalService(Object... objs){
        for (int i = 0; i < objs.length; i++) {
            Object obj = objs[i];
            String interfaceName = obj.getClass().getInterfaces()[0].getName();
            serviceContainer.put(interfaceName,obj);
        }
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Request msg) throws Exception {

        logger.info("hello invoke......");

        Object obj = serviceContainer.get(msg.getInterfaceName());

        Class clz = Class.forName(msg.getInterfaceName());

        Method method = clz.getMethod(msg.getMethodName(), String.class);
        Object invoke = method.invoke(obj, "bazinga");


        Response response = new Response();
        response.setId(msg.getId());
        response.setResult(invoke);

        System.out.println(response);

        ctx.channel().writeAndFlush(response);

    }

}
