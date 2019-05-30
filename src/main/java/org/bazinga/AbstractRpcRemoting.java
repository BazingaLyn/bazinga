package org.bazinga;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import org.bazinga.thread.NamedThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class AbstractRpcRemoting extends ChannelDuplexHandler implements Disposable {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractRpcRemoting.class);


    protected final ScheduledExecutorService timerExecutor = new ScheduledThreadPoolExecutor(1,
            new NamedThreadFactory("timeoutChecker", 1, true));


    protected final ThreadPoolExecutor messageExecutor;


    protected final ConcurrentHashMap<Long, MessageFuture> futures = new ConcurrentHashMap<Long, MessageFuture>();

    protected final ConcurrentHashMap<String, BlockingQueue<RpcMessage>> basketMap
            = new ConcurrentHashMap<String, BlockingQueue<RpcMessage>>();


    private static final long NOT_WRITEABLE_CHECK_MILLS = 10L;

    /**
     * The Merge lock.
     */
    protected final Object mergeLock = new Object();
    /**
     * The Now mills.
     */
    protected volatile long nowMills = 0;
    private static final int TIMEOUT_CHECK_INTERNAL = 3000;
    private final Object lock = new Object();
    /**
     * The Is sending.
     */
    protected volatile boolean isSending = false;
    private String group = "DEFAULT";
    /**
     * The Merge msg map.
     */
    protected final Map<Long, MergeMessage> mergeMsgMap = new ConcurrentHashMap<Long, MergeMessage>();
    /**
     * The Channel handlers.
     */
    protected ChannelHandler[] channelHandlers;

    /**
     * Instantiates a new Abstract rpc remoting.
     *
     * @param messageExecutor the message executor
     */
    public AbstractRpcRemoting(ThreadPoolExecutor messageExecutor) {
        this.messageExecutor = messageExecutor;
    }


    public void init() {
        timerExecutor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                List<MessageFuture> timeoutMessageFutures = new ArrayList<MessageFuture>(futures.size());

                for (MessageFuture future : futures.values()) {
                    if (future.isTimeout()) {
                        timeoutMessageFutures.add(future);
                    }
                }

                for (MessageFuture messageFuture : timeoutMessageFutures) {
                    futures.remove(messageFuture.getRequestMessage().getId());
                    messageFuture.setResultMessage(null);
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("timeout clear future : " + messageFuture.getRequestMessage().getBody());
                    }
                }
                nowMills = System.currentTimeMillis();
            }
        }, TIMEOUT_CHECK_INTERNAL, TIMEOUT_CHECK_INTERNAL, TimeUnit.MILLISECONDS);
    }

    @Override
    public void destroy() {
        timerExecutor.shutdown();
    }
}
