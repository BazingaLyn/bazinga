package org.bazinga.rpc;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import org.bazinga.BazingaClientHandler;
import org.bazinga.BazingaNettyClient;
import org.bazinga.domain.Request;
import org.bazinga.domain.Response;
import org.bazinga.domain.ResponseFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liguolin
 * @create 2018-11-22 13:40
 **/
public class NettyRpcClient {

    private static Logger logger = LoggerFactory.getLogger(NettyRpcClient.class);


    public Map<Long,ResponseFuture> maps = new ConcurrentHashMap<>();


    public static void main(String[] args) throws InterruptedException {

        NettyRpcClient nettyRpcClient = new NettyRpcClient();

        BazingaClientHandler bazingaClientHandler = new BazingaClientHandler(nettyRpcClient);

        BazingaNettyClient bazingaNettyClient  = new BazingaNettyClient(bazingaClientHandler);

        Request request = new Request();
        request.setId(1234567L);
        request.setInterfaceName("org.bazinga.service.HelloService");
        request.setMethodName("hello");
        request.setParams(new Object[]{"bazinga"});
        Response response = nettyRpcClient.handlerRequest(bazingaNettyClient,"127.0.0.1:18899", request);

        logger.info("last receive {}",response);

    }


    public Response handlerRequest(BazingaNettyClient bazingaNettyClient,String address, final Request request) throws InterruptedException {

        Channel channel = bazingaNettyClient.doCreateChannel(address);

        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
        }

        ResponseFuture responseFuture = new ResponseFuture();

        maps.put(request.getId(),responseFuture);

        channel.writeAndFlush(request).addListener(future -> {

            if(!future.isSuccess()){
                maps.remove(request.getId());
                Response response = new Response();
                response.setId(request.getId());
                response.setThrowable(future.cause());
                responseFuture.putResponse(null);
            }

        });

        return responseFuture.waitResponse();
    }

    public Map<Long, ResponseFuture> getMaps() {
        return maps;
    }

    public void setMaps(Map<Long, ResponseFuture> maps) {
        this.maps = maps;
    }
}
