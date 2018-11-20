package org.bazinga;

import java.util.Arrays;
import java.util.List;

/**
 * @author liguolin
 * @create 2018-11-19 19:58
 **/
public class ServiceInfo {

    private String interfaceName;

    private String methodName;

    private Class<?>[] paramters;

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParamters() {
        return paramters;
    }

    public void setParamters(Class<?>[] paramters) {
        this.paramters = paramters;
    }

    @Override
    public String toString() {
        return "ServiceInfo{" +
                "interfaceName='" + interfaceName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", paramters=" + Arrays.toString(paramters) +
                '}';
    }
}
