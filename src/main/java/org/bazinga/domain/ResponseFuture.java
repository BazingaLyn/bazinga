package org.bazinga.domain;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author liguolin
 * @create 2018-11-23 17:01
 **/
public class ResponseFuture {

    private Response response;


    private final CountDownLatch countDownLatch = new CountDownLatch(1);

    private long timeoutMillis = 10000L;

    public ResponseFuture() {
    }

    public Response waitResponse() throws InterruptedException{
        this.countDownLatch.await(this.timeoutMillis, TimeUnit.MILLISECONDS);
        return this.response;
    }

    public void putResponse(final Response response){
        this.response = response;
        this.countDownLatch.countDown();
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public CountDownLatch getCountDownLatch() {
        return countDownLatch;
    }
}
