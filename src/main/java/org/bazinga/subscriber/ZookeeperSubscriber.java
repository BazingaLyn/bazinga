package org.bazinga.subscriber;

import org.bazinga.rpc.ServiceRegisterInfo;

import java.util.List;

/**
 * @author liguolin
 * @create 2018-11-20 18:05
 **/
public class ZookeeperSubscriber implements Subscriber {

    private String zookeeperAddress;

    @Override
    public Subscriber address(String address) {
        this.zookeeperAddress = address;
        return this;
    }

    @Override
    public List<ServiceRegisterInfo> subscribe(String interfaceName) {
        return null;
    }
}
