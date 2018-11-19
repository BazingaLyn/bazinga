package org.bazinga;

/**
 * @author liguolin
 * @create 2018-11-19 17:32
 **/
public class DemoServiceImpl implements DemoService {

    @Override
    public String hello(String str) {
        return "hello"+str;
    }
}
