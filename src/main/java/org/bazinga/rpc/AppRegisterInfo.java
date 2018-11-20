package org.bazinga.rpc;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liguolin
 * @create 2018-11-20 15:41
 **/
public class AppRegisterInfo {

    private String applicationName;

    private String ip;

    private Map<String,ServiceRegisterInfo> serviceMaps = new ConcurrentHashMap<>(16);
}
