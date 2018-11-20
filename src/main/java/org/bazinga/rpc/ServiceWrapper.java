package org.bazinga.rpc;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 服务信息
 * @author liguolin
 * @create 2018-11-20 15:29
 **/
public class ServiceWrapper<T> {

    private T ref;

    private Class<T> clazz;

    private List<Method> methods;


}
