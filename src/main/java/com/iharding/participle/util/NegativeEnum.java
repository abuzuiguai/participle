package com.iharding.participle.util;

/**
 * Created by Administrator on 2017/11/8.
 */
public enum NegativeEnum {
    SYMPTOM("1"), BODY_PART("2"), EVENT_TIME("3"), CAUSE("4"), DEEP("5"), PROPERTY("6"), FEMININE("7"),
    VITAL_NAME("12"), VITAL_RESULT("13"),
    PACS_NAME("54"), PACS_RESULT("55"),
    OTHER("99");
    private String value;

    NegativeEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }

    public static NegativeEnum parseOfValue(String value) {
        NegativeEnum negativeEnum = NegativeEnum.OTHER;
        switch (value) {
            case "1":
                negativeEnum = NegativeEnum.SYMPTOM;
                break;
            case "2":
                negativeEnum = NegativeEnum.BODY_PART;
                break;
            case "3":
                negativeEnum = NegativeEnum.EVENT_TIME;
                break;
            case "4":
                negativeEnum = NegativeEnum.CAUSE;
                break;
            case "5":
                negativeEnum = NegativeEnum.DEEP;
                break;
            case "6":
                negativeEnum = NegativeEnum.PROPERTY;
                break;
            case "7":
                negativeEnum = NegativeEnum.FEMININE;
                break;
            case "12":
                negativeEnum = NegativeEnum.VITAL_NAME;
                break;
            case "13":
                negativeEnum = NegativeEnum.VITAL_RESULT;
                break;
            case "54":
                negativeEnum = NegativeEnum.PACS_NAME;
                break;
            case "55":
                negativeEnum = NegativeEnum.PACS_RESULT;
                break;
            case "99":
                negativeEnum = NegativeEnum.OTHER;
                break;
        }
        return negativeEnum;
    }
}
