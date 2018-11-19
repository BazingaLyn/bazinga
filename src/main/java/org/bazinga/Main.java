package org.bazinga;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author liguolin
 * @create 2018-11-19 17:33
 **/
public class Main {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {

//        Class<?> aClass = Class.forName("org.bazinga.DemoServiceImpl");
//
//        Method method = aClass.getDeclaredMethod("hello",new Class[]{String.class});
//
//        Object o = method.invoke(aClass.newInstance(),"hello");
//
//        System.out.println(o.toString());

        DemoService demoService = (DemoService)Proxy.newProxyInstance(DemoService.class.getClassLoader(),new Class<?>[]{DemoService.class},new JdkProxyHandler(new DemoServiceImpl()));


        String hello = demoService.hello("hello");
        System.out.println(hello);


    }

    static class JdkProxyHandler implements InvocationHandler {

        private Object target;

        public JdkProxyHandler(Object target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return method.invoke(target,args);
        }
    }
}
