package org.bazinga.rpc;

/**
 * @author liguolin
 * @create 2018-11-20 18:01
 **/
public interface Response {

    Object result();


    Exception exception();


    long requestId();



}
