package org.bazinga;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author liguolin
 * @create 2018-11-29 11:06
 **/
public class NioClient {

    public static void main(String[] args) throws IOException {

        SocketChannel socketChannel = SocketChannel.open();
        boolean connect = socketChannel.connect(new InetSocketAddress("127.0.0.1", 8888));

        if(connect){
            ByteBuffer byteBuffer = ByteBuffer.allocate(128);
            byteBuffer.put("Hello java nio".getBytes());

            byteBuffer.flip();

            socketChannel.write(byteBuffer);
            socketChannel.close();
        }
    }
}
