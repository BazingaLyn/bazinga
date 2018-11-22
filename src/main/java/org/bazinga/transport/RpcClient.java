package org.bazinga.transport;

import io.netty.channel.Channel;

/**
 * @author liguolin
 * @create 2018-11-20 17:45
 **/
public interface RpcClient {

    void init();

    void start();

    Channel getAvailableChannel(String address);



}
