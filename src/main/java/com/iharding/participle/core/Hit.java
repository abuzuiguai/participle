package com.iharding.participle.core;

/**
 * Created by Administrator on 2017/10/23.
 */
public class Hit {

    private int end;
    private int length;

    public Hit(int end, int length) {
        this.end = end;
        this.length = length;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

}
