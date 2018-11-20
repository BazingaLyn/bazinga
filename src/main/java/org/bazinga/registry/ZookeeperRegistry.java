package org.bazinga.registry;

import org.apache.curator.framework.CuratorFramework;
import org.bazinga.config.Config;
import org.bazinga.rpc.AppRegisterInfo;

/**
 * @author liguolin
 * @create 2018-11-20 15:48
 **/
public class ZookeeperRegistry implements Registry {

    private String registerAddress;

    private CuratorFramework client;

    private Config config;


    public ZookeeperRegistry(String address){
        this.registerAddress = address;
    }

    @Override
    public void init() {

    }

    @Override
    public Registry config(Config config) {
        this.config = config;
        return this;
    }

    @Override
    public void register(AppRegisterInfo appRegisterInfo) {

    }
}
