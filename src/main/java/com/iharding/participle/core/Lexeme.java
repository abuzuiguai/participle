package com.iharding.participle.core;

/**
 * Created by Administrator on 2017/10/23.
 */
public class Lexeme implements Comparable<Lexeme> {
    private int offset = 0;
    private int length = 0;

    private String text;
    private String type;

    public Lexeme(int offset, int length) {
        this.offset = offset;
        this.length = length;
    }
    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int compareTo(Lexeme o) {
        return  this.offset - o.getOffset();
    }
}
