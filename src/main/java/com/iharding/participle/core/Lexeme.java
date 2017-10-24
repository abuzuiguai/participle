package com.iharding.participle.core;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Administrator on 2017/10/23.
 */
public class Lexeme {
    private int offset = 0;
    private int length = 0;

    private char[] hitedChar;
    private String text;
    private String type;

    public Lexeme(int offset, int length) {
        this.offset = offset;
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
}
