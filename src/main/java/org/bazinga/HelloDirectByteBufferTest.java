package org.bazinga;

import org.junit.Test;
import sun.nio.ch.DirectBuffer;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liguolin
 * @create 2018-10-30 9:57
 **/
public class HelloDirectByteBufferTest {

    @Test
    public void test001(){
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(5);
        byteBuffer.put((byte)2);
        byteBuffer.put((byte)3);
        byteBuffer.put((byte)4);
        byteBuffer.put((byte)5);
        byteBuffer.put((byte)6);
        System.out.println(byteBuffer.position());
        byteBuffer.flip();
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());
    }



    @Test
    public void test002() throws InterruptedException {
        List<ByteBuffer> bufferLists = new ArrayList<ByteBuffer>();
        //-XMX6M -XX:MaxDirectMemorySize=10M
        for (int i = 0;i <5; i++){
            Thread.sleep(5000);
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024 *1024 * 1);
            bufferLists.add(byteBuffer);
        }

        bufferLists.forEach(eachByteBuffer -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ((DirectBuffer)eachByteBuffer).cleaner().clean();
        });

        Thread.sleep(10000);
    }


    @Test
    public void test003() throws InterruptedException {
        List<ByteBuffer> bufferLists = new ArrayList<ByteBuffer>();
        //-XMX6M -XX:MaxDirectMemorySize=10M
        for (int i = 0;i <5; i++){
            Thread.sleep(5000);
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024 *1024 * 1);
            bufferLists.add(byteBuffer);
        }

        bufferLists = null;
        Thread.sleep(10000);
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024 *1024 * 6);
        Thread.sleep(10000);
        byteBuffer = null;
        Thread.sleep(10000);
        byteBuffer = ByteBuffer.allocateDirect(1024 *1024 * 9);
        Thread.sleep(10000);
    }
}
