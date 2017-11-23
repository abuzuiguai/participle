package com.iharding.participle.util;

/**
 * Created by Administrator on 2017/11/8.
 */
public enum NegativeEnum {
    SYMPTOM("1"), EVENT_TIME("2"), BODY_PART("3"), PROPERTY("4"), CAUSE("5"), DEEP("6"), FEMININE("7"), POSITIVE("8"),
    UNIT("9"), MEDICINE("10"), TREATMENT("11"),
    VITAL_NAME("12"), VITAL_RESULT("13"),
    LIS_NAME("14"), LIS_RESULT("15"), WAY("16"),
    PACS_NAME("17"), PACS_RESULT("18"),
    DISEASE("19"), JOIN("20"),
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
                negativeEnum = NegativeEnum.EVENT_TIME;
                break;
            case "3":
                negativeEnum = NegativeEnum.BODY_PART;
                break;
            case "4":
                negativeEnum = NegativeEnum.PROPERTY;
                break;
            case "5":
                negativeEnum = NegativeEnum.CAUSE;
                break;
            case "6":
                negativeEnum = NegativeEnum.DEEP;
                break;
            case "7":
                negativeEnum = NegativeEnum.FEMININE;
                break;
            case "8":
                negativeEnum = NegativeEnum.POSITIVE;
                break;
            case "9":
                negativeEnum = NegativeEnum.UNIT;
                break;
            case "10":
                negativeEnum = NegativeEnum.MEDICINE;
                break;
            case "11":
                negativeEnum = NegativeEnum.TREATMENT;
                break;
            case "12":
                negativeEnum = NegativeEnum.VITAL_NAME;
                break;
            case "13":
                negativeEnum = NegativeEnum.VITAL_RESULT;
                break;
            case "14":
                negativeEnum = NegativeEnum.LIS_NAME;
                break;
            case "15":
                negativeEnum = NegativeEnum.LIS_RESULT;
                break;
            case "16":
                negativeEnum = NegativeEnum.WAY;
                break;
            case "17":
                negativeEnum = NegativeEnum.PACS_NAME;
                break;
            case "18":
                negativeEnum = NegativeEnum.PACS_RESULT;
                break;
            case "19":
                negativeEnum = NegativeEnum.DISEASE;
                break;
            case "20":
                negativeEnum = NegativeEnum.JOIN;
                break;
            case "99":
                negativeEnum = NegativeEnum.OTHER;
                break;
        }
        return negativeEnum;
    }
}
