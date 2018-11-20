package org.bazinga.rpc;

/**
 * @author liguolin
 * @create 2018-11-20 15:29
 **/
public class DefaultRequest implements Request{


    @Override
    public long requestId() {
        return 0;
    }

    @Override
    public String interfaceName() {
        return null;
    }

    @Override
    public String methodName() {
        return null;
    }

    @Override
    public Object[] paramters() {
        return new Object[0];
    }
}
