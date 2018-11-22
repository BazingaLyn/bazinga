package org.bazinga.rpc;

/**
 * @author liguolin
 * @create 2018-11-21 19:30
 **/
public class DefaultResponse implements Response {

    @Override
    public Object result() {
        return null;
    }

    @Override
    public Exception exception() {
        return null;
    }

    @Override
    public long requestId() {
        return 0;
    }
}
