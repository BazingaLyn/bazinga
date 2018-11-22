package org.bazinga;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author liguolin
 * @create 2018-11-22 10:30
 **/
public class Subscriber {

    public static void main(String[] args) throws Exception {

        CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder()
                .connectString("47.98.164.130:2181")
                .retryPolicy(new ExponentialBackoffRetry(1000, 3, 5000))
                .namespace("bazinga");


        CuratorFramework client = builder.build();

        client.start();

        PathChildrenCache pathChildrenCache = new PathChildrenCache(client,"/org.bazinga.HelloService",true);
        pathChildrenCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);

        ExecutorService pool = Executors.newFixedThreadPool(1);
        pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {

                switch (pathChildrenCacheEvent.getType()){
                    case CHILD_ADDED:
                        System.out.println("CHILD_ADDED: " + pathChildrenCacheEvent.getData().getPath());
                        break;
                    case CHILD_REMOVED:
                        System.out.println("CHILD_REMOVED: " + pathChildrenCacheEvent.getData().getPath());
                        break;
                    case CHILD_UPDATED:
                        System.out.println("CHILD_UPDATED: " + pathChildrenCacheEvent.getData().getPath());
                        break;
                    default:
                        break;
                }
            }
        },pool);

        Thread.sleep(Integer.MAX_VALUE);



    }
}
