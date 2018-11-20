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

    public T getRef() {
        return ref;
    }

    public void setRef(T ref) {
        this.ref = ref;
    }

    public Class<T> getClazz() {
        return clazz;
    }

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }

    public List<Method> getMethods() {
        return methods;
    }

    public void setMethods(List<Method> methods) {
        this.methods = methods;
    }
}
