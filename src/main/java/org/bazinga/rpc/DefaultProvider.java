package org.bazinga.rpc;

import org.bazinga.registry.Registry;
import org.bazinga.transport.RpcServer;

/**
 * @author liguolin
 * @create 2018-11-20 15:51
 **/
public class DefaultProvider implements Provider {

    private String appName;

    private String serviceName;

    private Registry registry;

    private RpcServer rpcServer;

    @Override
    public Provider app(String appName) {
        this.appName = appName;
        return this;
    }

    @Override
    public Provider address(String address) {
        this.serviceName = address;
        return this;
    }

    @Override
    public Provider registry(Registry registry) {
        this.registry = registry;
        return this;
    }

    @Override
    public Provider rpcServer(RpcServer rpcServer) {
        this.rpcServer = rpcServer;
        return this;
    }

    @Override
    public Response handlerRpcRequest(Request request) {
        return null;
    }

    @Override
    public void init() {


    }

    @Override
    public void start() {

    }
}
