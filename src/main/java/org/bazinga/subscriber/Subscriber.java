package org.bazinga.subscriber;

import org.bazinga.rpc.ServiceRegisterInfo;

import java.util.List;

/**
 * @author liguolin
 * @create 2018-11-20 18:05
 **/
public interface Subscriber {

    Subscriber address(String address);

    List<ServiceRegisterInfo> subscribe(String interfaceName);

}
