package org.bazinga;

import org.bazinga.domain.ReconnectTask;
import org.bazinga.domain.Url;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * 重连管理器
 *
 * @author liguolin
 * @create 2018-10-25 9:29
 **/
public class ReconnectManager {

    private static final Logger logger = LoggerFactory.getLogger(ReconnectManager.class);

    private final LinkedBlockingQueue<ReconnectTask> tasks = new LinkedBlockingQueue<>();

    private int eachDefaultRetryTime;

    private static final int DEFAULTRETRYTIME = 5;

    private volatile boolean started;

    private volatile long minRetryTimeIntervalUnit = 200L;

    private NettyConnector nettyConnector;

    private Thread reConnectionThreads;

    public ReconnectManager(NettyConnector nettyConnector) {
        this(nettyConnector,DEFAULTRETRYTIME);
    }

    public ReconnectManager(NettyConnector nettyConnector,int retryTime) {
        this.nettyConnector = nettyConnector;
        this.eachDefaultRetryTime = retryTime;
        reConnectionThreads = new Thread(new ReConnectionRunner());
        reConnectionThreads.start();
        this.started = true;

    }

    public void addReconnectTask(Url url) {
        ReconnectTask reconnectTask = new ReconnectTask(eachDefaultRetryTime,url);
        tasks.add(reconnectTask);
    }

    private final class ReConnectionRunner implements Runnable {

        @Override
        public void run() {

            while (ReconnectManager.this.started) {

                ReconnectTask task = null;
                try {
                     task = ReconnectManager.this.tasks.take();

                     if(null != task){

                         int beginRetryTime = 0;
                         int retryCount = task.getMaxRetryCount();

                         Url url = task.getUrl();

                         while(beginRetryTime < retryCount){
                             try {

                                 Thread.sleep(minRetryTimeIntervalUnit * (beginRetryTime +1));
                                 ReconnectManager.this.nettyConnector.connect(url.getHost(),url.getPort());
                                 break;
                             } catch (Exception e) {
                                 ++beginRetryTime;
                                 logger.info("URL {} and retryTime is {} times",url,beginRetryTime);
                                 // 如果失败 就重新放回到队列里面
                                 if(beginRetryTime == 5){
                                     ReconnectManager.this.addReconnectTask(url);
                                 }
                             }
                         }
                     }
                } catch (InterruptedException e) {
                    //ignore
                }
            }
        }
    }

}
