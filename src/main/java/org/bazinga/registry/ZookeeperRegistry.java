package org.bazinga.registry;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.bazinga.config.Config;
import org.bazinga.rpc.AppRegisterMeta;
import org.bazinga.rpc.ServiceRegisterMeta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author liguolin
 * @create 2018-11-20 15:48
 **/
public class ZookeeperRegistry implements Registry {

    private Logger logger = LoggerFactory.getLogger(ZookeeperRegistry.class);

    private String registerAddress;

    private CuratorFramework client;

    private Config config;

    private String namespace;


    public ZookeeperRegistry(String address,String namespace){
        this.namespace = namespace;
        this.registerAddress = address;
    }

    @Override
    public void init() {
        CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder()
                .connectString(registerAddress)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3, 5000))
                .namespace(namespace);

        client = builder.build();

        client.start();
    }

    @Override
    public Registry config(Config config) {
        this.config = config;
        return this;
    }

    @Override
    public void register(AppRegisterMeta appRegisterMeta) {

        final Map<String, ServiceRegisterMeta> serviceMaps = appRegisterMeta.getServiceMaps();

        if(null != serviceMaps && serviceMaps.keySet().size() > 0){

            for(String interfaceName : serviceMaps.keySet()){
                doRegister(serviceMaps, interfaceName);
            }
        }
    }

    private void doRegister(Map<String, ServiceRegisterMeta> serviceMaps, String interfaceName) {
        ServiceRegisterMeta meta = serviceMaps.get(interfaceName);
        try {
            if(client.checkExists().forPath(interfaceName) == null){
                String path = String.format("%s/%s",interfaceName,meta.getAddress());
                client.create().creatingParentContainersIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path);
            }
        } catch (Exception e) {
            logger.error("register appRegisterMeta failed {} ",meta,e);
        }
    }
}
