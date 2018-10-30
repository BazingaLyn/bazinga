package org.bazinga;

import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class NewIOClient {

    public static void main(String[] args) throws Exception {

        Thread.sleep(10000);
        for (int i = 0; i < 5; i++) {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress("localhost", 8899));
            socketChannel.configureBlocking(true);

            String fileName = "C:\\ideaIU-2018.2.5.exe";

            FileChannel fileChannel = new FileInputStream(fileName).getChannel();

            long startTime = System.currentTimeMillis();

            long transferCount = fileChannel.transferTo(0, fileChannel.size(), socketChannel);

            System.out.println("发送总字节数：" + transferCount + "，耗时： " + (System.currentTimeMillis() - startTime));

            fileChannel.close();
            socketChannel.close();
        }

        Thread.sleep(20000);

    }
}
