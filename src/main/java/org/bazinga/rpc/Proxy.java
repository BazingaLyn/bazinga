package org.bazinga.rpc;

/**
 * @author liguolin
 * @create 2018-11-20 17:23
 **/
public class Proxy<T> {

    private Consumer consumer;

    private Class clazz;

    public static<T> T newInstance(){
        return null;
    }

    public static final class ProxyBuilder {
        private Consumer consumer;

        private ProxyBuilder() {
        }

        public static ProxyBuilder aProxy() {
            return new ProxyBuilder();
        }

        public ProxyBuilder consumer(Consumer consumer) {
            this.consumer = consumer;
            return this;
        }

        public Proxy build() {
            Proxy proxy = new Proxy();
            proxy.consumer = this.consumer;
            return proxy;
        }
    }
}
