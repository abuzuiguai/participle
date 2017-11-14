package com.iharding.participle.core;

/**
 * 词元定义.
 */
public class Lexeme implements Comparable<Lexeme> {
    private int offset = 0;
    private int length = 0;

    private String text;
    private String property;
    //段落分割 成句 '。',';','；','?' = -1 , ',','，',':','：' = -2
    private String flag;

    private float threshold;

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

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public float getThreshold() { return threshold; }

    public void setThreshold(float threshold) {
        this.threshold = threshold;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public int compareTo(Lexeme o) {
        return  this.offset - o.getOffset();
    }
}
