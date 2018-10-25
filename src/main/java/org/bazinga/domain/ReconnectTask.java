package org.bazinga.domain;

/**
 * @author liguolin
 * @create 2018-10-25 9:38
 **/
public class ReconnectTask {

    private int maxRetryCount;

    private Url url;

    public ReconnectTask(int maxRetryCount, Url url) {
        this.maxRetryCount = maxRetryCount;
        this.url = url;
    }

    public int getMaxRetryCount() {
        return maxRetryCount;
    }

    public void setMaxRetryCount(int maxRetryCount) {
        this.maxRetryCount = maxRetryCount;
    }

    public Url getUrl() {
        return url;
    }

    public void setUrl(Url url) {
        this.url = url;
    }
}
