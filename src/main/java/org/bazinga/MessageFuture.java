package org.bazinga;

import org.bazinga.exception.ShouldNeverHappenException;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MessageFuture {

    private RpcMessage requestMessage;
    private long timeout;
    private long start = System.currentTimeMillis();
    private static final Object NULL = new Object();
    private transient CompletableFuture origin = new CompletableFuture();

    public boolean isTimeout(){
        return System.currentTimeMillis() -start > timeout;
    }

    public Object get(long timeout, TimeUnit unit) throws TimeoutException,InterruptedException {
        Object result = null;

        try {
            result = origin.get(timeout,unit);
        } catch (ExecutionException e) {
            throw new ShouldNeverHappenException("Should not get results in a multi-threaded environment", e);
        } catch (TimeoutException e) {
            throw new TimeoutException("cost " + (System.currentTimeMillis() - start) + " ms");
        }

        if (result instanceof RuntimeException) {
            throw (RuntimeException)result;
        } else if (result instanceof Throwable) {
            throw new RuntimeException((Throwable)result);
        }

        return result;
    }

    public void setResultMessage(Object obj) {
        origin.complete(obj);
    }


    public RpcMessage getRequestMessage() {
        return requestMessage;
    }

    public void setRequestMessage(RpcMessage requestMessage) {
        this.requestMessage = requestMessage;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }
}
