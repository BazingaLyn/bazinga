package org.bazinga.rpc;

/**
 * @author liguolin
 * @create 2018-11-20 15:16
 **/
public interface Request {

    long requestId();


    String interfaceName();


    String methodName();


    Object[] paramters();

}
