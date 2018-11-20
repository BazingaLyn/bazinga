package org.bazinga;

import org.bazinga.rpc.DefaultConsumer;
import org.bazinga.rpc.DefaultProvider;

/**
 * @author liguolin
 * @create 2018-11-20 17:29
 **/
public class BazingaSimpleServer {

    public static void main(String[] args) {

        HelloService helloService = new HelloServiceImpl();

        DefaultProvider defaultProvider = new DefaultProvider();




    }
}
