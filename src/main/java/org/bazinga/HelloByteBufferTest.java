package org.bazinga;

import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

/**
 * @author bazinga
 * @create 2018-10-26 11:13
 **/
public class HelloByteBufferTest {


    @Test
    public void test001(){
        ByteBuffer heapByteBuffer = ByteBuffer.allocate(20);

        System.out.printf("before write position is %s limit is %s capacity is %s\n",
                heapByteBuffer.position(),
                heapByteBuffer.limit(),
                heapByteBuffer.capacity());

        heapByteBuffer.putInt(2);
        heapByteBuffer.putInt(2);

        System.out.printf("writing position is %s limit is %s capacity is %s\n",
                heapByteBuffer.position(),
                heapByteBuffer.limit(),
                heapByteBuffer.capacity());
        heapByteBuffer.putInt(2);
        heapByteBuffer.putInt(2);
        heapByteBuffer.putInt(2);

        System.out.printf("after writing position is %s limit is %s capacity is %s\n",
                heapByteBuffer.position(),
                heapByteBuffer.limit(),
                heapByteBuffer.capacity());

        heapByteBuffer.flip();
        System.out.printf("after flip position is %s limit is %s capacity is %s\n",
                heapByteBuffer.position(),
                heapByteBuffer.limit(),
                heapByteBuffer.capacity());
        System.out.println(heapByteBuffer.getInt());

        System.out.printf("after read position is %s limit is %s capacity is %s\n",
                heapByteBuffer.position(),
                heapByteBuffer.limit(),
                heapByteBuffer.capacity());
    }


    @Test
    public void test002(){
        IntBuffer intBuffer = IntBuffer.allocate(2);
        intBuffer.put(32);
        intBuffer.put(34);
        intBuffer.flip();
        System.out.println(intBuffer.get());
        System.out.println(intBuffer.get());
    }




    @Test
    public void test003(){

        IntBuffer intBuffer = IntBuffer.allocate(20);

        // 开始写的模式，position表示当前写的位置，当前还没有写，所以position是0
        // 我们写了四个，position到了4 limit和capacity没有变，都等于20
        intBuffer.put(32);
        intBuffer.put(34);
        intBuffer.put(36);
        intBuffer.put(38);

        //在读的模式下，20 - 4
        System.out.println(intBuffer.remaining());
        // 从写的模式变成了读的模式capacity没有变 因为容量并没有变，position变成了0，因为需要开始从当初写的
        //地方开始读，所以position变成了0，limit 这时候的意思是最多读多少位，最多当然只能读到我们当初写的未知
        // 也就是4，所以limit 等于4
        intBuffer.flip();
        System.out.println(intBuffer.get());
        System.out.println(intBuffer.get());
        System.out.println(intBuffer.remaining());

        // 截取position到limit之间的数据
        IntBuffer childBuffer = intBuffer.slice();
        System.out.println(childBuffer.get());
        // 在读取的模式下，还有多少可读 1
        System.out.println(childBuffer.remaining());
    }

    @Test
    public void test004(){
        ByteBuffer byteBuffer = ByteBuffer.allocate(1);
        byteBuffer.put((byte)1);
        ByteBuffer asReadOnlyBuffer = byteBuffer.asReadOnlyBuffer();
        System.out.println(asReadOnlyBuffer.getClass());
        //报错
        //asReadOnlyBuffer.put((byte)2);
    }


    @Test
    public void test005(){
        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[]{(byte)1,(byte)2});

        System.out.println(byteBuffer.getClass());
        System.out.printf("position is %s limit is %s capacity is %s\n",
                byteBuffer.position(),
                byteBuffer.limit(),
                byteBuffer.capacity());

        byteBuffer.flip();
        System.out.printf("after flip position is %s limit is %s capacity is %s\n",
                byteBuffer.position(),
                byteBuffer.limit(),
                byteBuffer.capacity());
        byteBuffer.clear();

        System.out.printf("after clear position is %s limit is %s capacity is %s\n",
                byteBuffer.position(),
                byteBuffer.limit(),
                byteBuffer.capacity());
    }
}
