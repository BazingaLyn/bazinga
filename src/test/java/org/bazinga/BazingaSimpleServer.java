package org.bazinga;

import org.bazinga.registry.Registry;
import org.bazinga.registry.ZookeeperRegistry;
import org.bazinga.rpc.DefaultProvider;
import org.bazinga.transport.DefaultRpcServer;
import org.bazinga.transport.RpcServer;

/**
 * @author liguolin
 * @create 2018-11-20 17:29
 **/
public class BazingaSimpleServer {


    private static String zookeeperAddress = "47.98.164.130:2181";

    private static String DEFAULT_NAMESPACE = "bazinga";

    public static void main(String[] args) {

        Registry registry = new ZookeeperRegistry(zookeeperAddress,DEFAULT_NAMESPACE);

        RpcServer rpcServer = new DefaultRpcServer(8089);

        HelloService helloService = new HelloServiceImpl();

        DefaultProvider defaultProvider = new DefaultProvider();

        defaultProvider.address("127.0.0.1:8089").app("bazinga-server").registry(registry).rpcServer(rpcServer);

        defaultProvider.providerService(helloService);

        defaultProvider.init();

        defaultProvider.start();

    }
}
