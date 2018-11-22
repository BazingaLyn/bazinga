package org.bazinga.rpc;

import org.bazinga.registry.Registry;
import org.bazinga.transport.RpcServer;
import org.bazinga.utils.IpUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import static org.bazinga.utils.ReflectUtils.*;

/**
 * @author liguolin
 * @create 2018-11-20 15:51
 **/
public class DefaultProvider implements Provider {

    private Map<String,ServiceWrapper> serviceWrapperMap = new ConcurrentHashMap<>(32);

    private String appName;

    private int port;

    private String serviceName;

    private Registry registry;

    private RpcServer rpcServer;

    @Override
    public Provider app(String appName) {
        this.appName = appName;
        return this;
    }

    @Override
    public Provider providerPort(int port) {
        this.port = port;
        return this;
    }

    @Override
    public Provider address(String address) {
        this.serviceName = address;
        return this;
    }

    @Override
    public Provider registry(Registry registry) {
        this.registry = registry;
        return this;
    }

    @Override
    public Provider rpcServer(RpcServer rpcServer) {
        this.rpcServer = rpcServer;
        return this;
    }

    @Override
    public Provider providerService(Object... o) {

        if(null == o){
            return this;
        }

        for (int i = 0; i < o.length; i++) {
            ServiceWrapper serviceWrapper = new ServiceWrapper();

            Class clazz = interfaceClz(o);
            String uniqueServiceName = clazz.getName();
            serviceWrapper.setRef(o);
            serviceWrapper.setClazz(clazz);
            serviceWrapper.setMethods(Arrays.asList(clazz.getMethods()));

            serviceWrapperMap.putIfAbsent(uniqueServiceName,serviceWrapper);
        }
        return this;
    }

    @Override
    public Response handlerRpcRequest(Request request) {
        return null;
    }

    @Override
    public void init() {

        registry.init();
        AppRegisterMeta appRegisterMeta = new AppRegisterMeta();
        appRegisterMeta.setApplicationName(appName);
        appRegisterMeta.setIp(IpUtils.getLocalIp());
        appRegisterMeta.setPort(port);

        Set<String> serviceNames = serviceWrapperMap.keySet();

        for(String serviceName : serviceNames){

            ServiceWrapper serviceWrapper = serviceWrapperMap.get(serviceName);

            ServiceRegisterMeta serviceRegisterMeta =  new ServiceRegisterMeta();
            serviceRegisterMeta.setAddress(String.format("%s:%s", appRegisterMeta.getIp(), appRegisterMeta.getPort()));

            serviceRegisterMeta.setInterfaceName(serviceName);
            serviceRegisterMeta.setMethodList(serviceWrapper.getMethods());
            appRegisterMeta.putSerivceRegisterInfo(serviceName, serviceRegisterMeta);

        }
        registry.register(appRegisterMeta);
        rpcServer.init();

    }

    @Override
    public void start() {
        rpcServer.start();
    }
}
