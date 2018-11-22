package org.bazinga;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

/**
 * @author liguolin
 * @create 2018-11-22 10:27
 **/
public class Registry {

    public static void main(String[] args) throws Exception {

        CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder()
                .connectString("47.98.164.130:2181")
                .retryPolicy(new ExponentialBackoffRetry(1000, 3, 5000))
                .namespace("bazinga");


        CuratorFramework client = builder.build();

        client.start();

        client.create().creatingParentContainersIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/org.bazinga.HelloService/192.168.1.1:18999");
        client.create().creatingParentContainersIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/org.bazinga.HelloService/192.168.1.2:18999");



    }
}
