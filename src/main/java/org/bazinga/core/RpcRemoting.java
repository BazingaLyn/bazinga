package org.bazinga.core;

/**
 * @author liguolin
 * @create 2018-11-27 16:15
 **/
public interface RpcRemoting {


    void oneway(String address,Object request) throws Exception;


    void invokeSync(String address,Object request) throws Exception;
}
