package com.iharding.participle.core.model;

/**
 * Created by Administrator on 2017/11/10.
 */
public class Vital {
    private String name;
    private String bodyPart;
    private String result;
    private String negative;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBodyPart() {
        return bodyPart;
    }

    public void setBodyPart(String bodyPart) {
        this.bodyPart = bodyPart;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String isNegative() {
        return negative;
    }

    public void setNegative(String negative) {
        this.negative = negative;
    }
}