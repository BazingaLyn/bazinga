package org.bazinga.rpc;

import org.bazinga.registry.Registry;
import org.bazinga.transport.RpcServer;

/**
 * @author liguolin
 * @create 2018-11-20 15:44
 **/
public interface Provider {


    Provider app(String appName);

    Provider providerPort(int port);

    Provider address(String address);

    Provider registry(Registry registry);

    Provider rpcServer(RpcServer rpcServer);

    Provider providerService(Object... o);

    Response handlerRpcRequest(Request request);

    void init();

    void start();

}
