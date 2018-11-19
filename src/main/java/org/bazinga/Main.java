package org.bazinga;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.util.List;

/**
 * @author liguolin
 * @create 2018-11-19 17:33
 **/
public class Main {

    public static void main(String[] args) throws Exception {

        CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder()
                .connectString("47.98.164.130:2181")
                .retryPolicy(new ExponentialBackoffRetry(1000, 3, 5000))
                .namespace("bazinga");


        CuratorFramework client = builder.build();

        client.start();

//        client.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath("/org.bazinga.service.HelloService","hello".getBytes());
//        client.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath("/org.bazinga.service.HelloService","hello1".getBytes());

        client.create().creatingParentContainersIfNeeded().withMode(CreateMode.EPHEMERAL).forPath("/service/org.bazinga.service.HelloService/11111","hello".getBytes());
        client.create().creatingParentContainersIfNeeded().withMode(CreateMode.EPHEMERAL).forPath("/service/org.bazinga.service.HelloService/22222","hello1".getBytes());
        //        client.create().withMode(CreateMode.EPHEMERAL).forPath("/service/org.bazinga.service.HelloService","hello1".getBytes());

        List<String> strings = client.getChildren().forPath("/service/org.bazinga.service.HelloService");

        strings.stream().forEach(System.out::println);

        Thread.sleep(200000L);

    }

}
