package org.bazinga.registry;

import org.bazinga.config.Config;
import org.bazinga.rpc.AppRegisterInfo;

/**
 * @author liguolin
 * @create 2018-11-20 15:47
 **/
public interface Registry {

    void init();

    Registry config(Config config);

    void register(AppRegisterInfo appRegisterInfo);
}
