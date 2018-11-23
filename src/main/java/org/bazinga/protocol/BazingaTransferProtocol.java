package org.bazinga.protocol;

/**
 * @author liguolin
 * @create 2018-10-24 10:38
 **/
public class BazingaTransferProtocol {

    /** 1 请求 2响应  **/
    private byte type;

    private int length;

    private byte[] contents;

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public byte[] getContents() {
        return contents;
    }

    public void setContents(byte[] contents) {
        this.contents = contents;
    }
}
