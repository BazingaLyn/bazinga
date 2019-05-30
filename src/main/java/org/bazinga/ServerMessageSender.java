package org.bazinga;

import io.netty.channel.Channel;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public interface ServerMessageSender {


    void sendResponse(long msgId, Channel channel, Object msg);


    Object sendSyncRequest(String resourceId, String clientId, Object message, long timeout)
            throws IOException, TimeoutException;


    Object sendSyncRequest(String resourceId, String clientId, Object message) throws IOException, TimeoutException;



}
