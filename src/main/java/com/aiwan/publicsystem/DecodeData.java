package com.aiwan.publicsystem;

import java.io.Serializable;

public class DecodeData implements Serializable {
    private static final long serialVersionUID = 7592930394427200495L;

    public static final short REGIST = 1;
    public static final short LOGIN = 2;
    public static final short MOVE = 3;
    public static final short SHIFT = 4;

    private short type;
    private int length;
    private byte[] data;

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
