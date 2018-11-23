package org.bazinga.domain;

import com.alibaba.fastjson.JSON;

/**
 * @author liguolin
 * @create 2018-11-22 13:32
 **/
public class Response {

    private long id;

    private Object result;

    private Throwable throwable;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
