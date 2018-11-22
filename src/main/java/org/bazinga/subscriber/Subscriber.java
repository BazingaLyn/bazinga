package org.bazinga.subscriber;

import org.bazinga.rpc.ServiceRegisterMeta;

import java.util.List;

/**
 * @author liguolin
 * @create 2018-11-20 18:05
 **/
public interface Subscriber {

    void init();

    Subscriber address(String address);

    List<ServiceRegisterMeta> subscribe(String interfaceName);

}
