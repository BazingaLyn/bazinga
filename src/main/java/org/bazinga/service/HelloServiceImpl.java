package org.bazinga.service;

/**
 * @author liguolin
 * @create 2018-11-23 14:40
 **/
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String msg) {
        return "hello"+msg;
    }
}
