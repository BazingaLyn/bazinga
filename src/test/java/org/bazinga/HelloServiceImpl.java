package org.bazinga;

/**
 * @author liguolin
 * @create 2018-11-20 17:29
 **/
public class HelloServiceImpl implements HelloService {

    @Override
    public String hello(String hello) {
        return "hello " + hello;
    }
}
