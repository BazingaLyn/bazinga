package org.bazinga.registry;

import org.bazinga.rpc.AppRegisterInfo;

/**
 * @author liguolin
 * @create 2018-11-20 15:48
 **/
public class ZookeeperRegistry implements Registry {

    private String registerAddress;


    @Override
    public Registry address(String address) {
        this.registerAddress = address;
        return this;
    }

    @Override
    public void register(AppRegisterInfo appRegisterInfo) {

    }
}
