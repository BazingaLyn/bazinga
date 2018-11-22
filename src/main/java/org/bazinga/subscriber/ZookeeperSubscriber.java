package org.bazinga.subscriber;

import org.apache.curator.framework.CuratorFramework;
import org.bazinga.rpc.ServiceRegisterMeta;

import java.util.List;

/**
 * @author liguolin
 * @create 2018-11-20 18:05
 **/
public class ZookeeperSubscriber implements Subscriber {

    private String zookeeperAddress;

    private CuratorFramework client;

    @Override
    public void init() {

    }

    @Override
    public Subscriber address(String address) {
        this.zookeeperAddress = address;
        return this;
    }

    @Override
    public List<ServiceRegisterMeta> subscribe(String interfaceName) {
        return null;
    }
}
