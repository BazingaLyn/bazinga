package org.bazinga.utils;

/**
 * @author liguolin
 * @create 2018-11-20 20:55
 **/
public class ReflectUtils {



    //FIXME
    public static Class interfaceClz(Object o){

        Class<?>[] interfaces = o.getClass().getInterfaces();
        if(interfaces != null && interfaces.length > 0){
            return interfaces[0];
        }
        return null;
    }
}
