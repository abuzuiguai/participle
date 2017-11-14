package com.iharding.participle.jdbc;

/**
 * Created by Administrator on 2017/11/8.
 */
public class LexiconFinal {
    private String name;
    private float threshold;
    private String negative;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getThreshold() {
        return threshold;
    }

    public void setThreshold(float threshold) {
        this.threshold = threshold;
    }

    public String getNegative() {
        return negative;
    }

    public void setNegative(String negative) {
        this.negative = negative;
    }
}
