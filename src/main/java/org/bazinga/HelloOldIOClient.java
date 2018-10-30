package org.bazinga;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.Socket;

/**
 * @author liguolin
 * @create 2018-10-30 15:02
 **/
public class HelloOldIOClient {

    public static void main(String[] args) throws Exception {
        Thread.sleep(10000L);
        for (int i = 0; i < 5; i++) {
        Socket socket = new Socket("localhost", 8899);

        String fileName = "C:\\ideaIU-2018.2.5.exe";
            InputStream inputStream = new FileInputStream(fileName);

            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            byte[] buffer = new byte[4096];
            long readCount;
            long total = 0;

            long startTime = System.currentTimeMillis();

            while ((readCount = inputStream.read(buffer)) >= 0) {
                total += readCount;
                dataOutputStream.write(buffer);
            }

            System.out.println("发送总字节数： " + total + ", 耗时： " + (System.currentTimeMillis() - startTime));

            dataOutputStream.close();
            socket.close();
            inputStream.close();
        }

        Thread.sleep(20000);

    }


}
