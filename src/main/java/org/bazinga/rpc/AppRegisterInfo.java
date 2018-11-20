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

    private int port;

    private Map<String,ServiceRegisterInfo> serviceMaps = new ConcurrentHashMap<>(16);


    public void putSerivceRegisterInfo(String serviceName,ServiceRegisterInfo serviceRegisterInfo){
        this.serviceMaps.putIfAbsent(serviceName,serviceRegisterInfo);
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Map<String, ServiceRegisterInfo> getServiceMaps() {
        return serviceMaps;
    }

    public void setServiceMaps(Map<String, ServiceRegisterInfo> serviceMaps) {
        this.serviceMaps = serviceMaps;
    }
}
