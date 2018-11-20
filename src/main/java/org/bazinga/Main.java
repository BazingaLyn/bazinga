package org.bazinga;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liguolin
 * @create 2018-11-19 17:33
 **/
public class Main {

    static CuratorFramework client = null;

    static{
                CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder()
                .connectString("47.98.164.130:2181")
                .retryPolicy(new ExponentialBackoffRetry(1000, 3, 5000))
                .namespace("bazinga");


        client = builder.build();

        client.start();
    }

    private static Map<String,Object> serviceContainer = new ConcurrentHashMap<>(4);

    public static void main(String[] args) throws Exception {

        DemoService demoService = new DemoServiceImpl();

        Class<?>[] interfaces = demoService.getClass().getInterfaces();

        Class clz = interfaces[0];

        String interfaceName = clz.getName();

        serviceContainer.put(interfaceName,demoService);
        Method[] methods = clz.getMethods();

        List<ServiceInfo> serviceInfos  = Lists.newArrayList();
        for(Method m : methods){
            ServiceInfo serviceInfo = new ServiceInfo();
            serviceInfo.setInterfaceName(interfaceName);
            serviceInfo.setMethodName(m.getName());
            serviceInfo.setParamters(m.getParameterTypes());
            serviceInfos.add(serviceInfo);
        }

        client.create().withMode(CreateMode.EPHEMERAL).forPath(String.format("/%s/%s",interfaceName ,UUID.randomUUID().toString()), JSON.toJSONString(serviceInfos).toString().getBytes());

        //服务消费者
        List<String> providerInfos = client.getChildren().forPath(DemoService.class.getName());

        if(null != providerInfos && providerInfos.size() > 0){

            providerInfos.forEach(providerInfo ->{
                List<ServiceInfo> providers = JSON.parseArray(providerInfo, ServiceInfo.class);
            });

        }


//
////        client.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath("/org.bazinga.service.HelloService","hello".getBytes());
////        client.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath("/org.bazinga.service.HelloService","hello1".getBytes());
//
//        client.create().creatingParentContainersIfNeeded().withMode(CreateMode.EPHEMERAL).forPath("/service/org.bazinga.service.HelloService/11111","hello".getBytes());
//        client.create().creatingParentContainersIfNeeded().withMode(CreateMode.EPHEMERAL).forPath("/service/org.bazinga.service.HelloService/22222","hello1".getBytes());
//        //        client.create().withMode(CreateMode.EPHEMERAL).forPath("/service/org.bazinga.service.HelloService","hello1".getBytes());
//
//        List<String> strings = client.getChildren().forPath("/service/org.bazinga.service.HelloService");
//
//        strings.stream().forEach(System.out::println);
//
//        Thread.sleep(200000L);

    }

}
