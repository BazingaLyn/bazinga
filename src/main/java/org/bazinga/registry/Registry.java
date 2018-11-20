package org.bazinga.registry;

import org.bazinga.rpc.AppRegisterInfo;

/**
 * @author liguolin
 * @create 2018-11-20 15:47
 **/
public interface Registry {


    Registry address(String address);

    void register(AppRegisterInfo appRegisterInfo);
}
