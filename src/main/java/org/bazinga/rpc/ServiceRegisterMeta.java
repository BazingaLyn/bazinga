package org.bazinga.rpc;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author liguolin
 * @create 2018-11-20 15:45
 **/
public class ServiceRegisterMeta {

    private String group;

    private String address;

    private String interfaceName;

    private String version;

    private List<Method> methodList;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public List<Method> getMethodList() {
        return methodList;
    }

    public void setMethodList(List<Method> methodList) {
        this.methodList = methodList;
    }
}
