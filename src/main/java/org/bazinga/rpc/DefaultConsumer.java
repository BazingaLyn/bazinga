package org.bazinga.rpc;

import org.bazinga.transport.RpcClient;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liguolin
 * @create 2018-11-20 15:52
 **/
public class DefaultConsumer implements Consumer {

    private RpcClient rpcClient;

    private Map<String,List<ServiceRegisterInfo>> serviceRegisters = new ConcurrentHashMap<>(16);


    @Override
    public List<ServiceRegisterInfo> subscribe(String interfaceName) {
        return null;
    }

    @Override
    public Consumer rpcClient(RpcClient rpcClient) {
        return this;
    }
}
