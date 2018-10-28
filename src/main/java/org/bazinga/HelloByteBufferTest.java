package org.bazinga;

import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * @author liguolin
 * @create 2018-10-26 11:13
 **/
public class HelloByteBufferTest {

    @Test
    public void test001(){
        ByteBuffer heapByteBuffer = ByteBuffer.allocate(20);
        heapByteBuffer.putInt(2);
        heapByteBuffer.putInt(2);
        heapByteBuffer.putInt(2);
        heapByteBuffer.putInt(2);
        heapByteBuffer.putInt(2);
        heapByteBuffer.flip();
        System.out.println(heapByteBuffer.getInt());
    }


    @Test
    public void test002(){

    }
}
