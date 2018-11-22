package org.bazinga.rpc;

import org.bazinga.transport.RpcClient;

import java.util.List;

/**
 * @author liguolin
 * @create 2018-11-20 15:44
 **/
public interface Consumer {

    List<ServiceRegisterMeta> subscribe(String interfaceName);

    Consumer rpcClient(RpcClient rpcClient);

}
