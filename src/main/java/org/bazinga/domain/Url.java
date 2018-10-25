package org.bazinga.domain;

/**
 * @author liguolin
 * @create 2018-10-24 18:00
 **/
public class Url {

    private String host;

    private int port;

    public Url(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "Url{" +
                "host='" + host + '\'' +
                ", port=" + port +
                '}';
    }
}
