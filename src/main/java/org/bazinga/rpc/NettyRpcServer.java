package org.bazinga.rpc;

import org.bazinga.BazingaNettyServer;
import org.bazinga.BazingaServerHandler;
import org.bazinga.service.HelloService;
import org.bazinga.service.HelloServiceImpl;

/**
 * @author liguolin
 * @create 2018-11-22 13:40
 **/
public class NettyRpcServer {

    static String host = "127.0.0.1";
    static int port = 18899;

    public static void main(String[] args) {

        HelloService helloService = new HelloServiceImpl();
        BazingaNettyServer bazingaNettyServer = new BazingaNettyServer();
        BazingaServerHandler bazingaServerHandler = new BazingaServerHandler();
        bazingaServerHandler.publishLocalService(helloService);

        bazingaNettyServer.bind(host,port,bazingaServerHandler);

    }

}
