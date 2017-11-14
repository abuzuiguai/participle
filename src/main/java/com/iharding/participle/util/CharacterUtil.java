package com.iharding.participle.util;

public class CharacterUtil {

    public static final int CHAR_USELESS = 0;

    public static final int CHAR_ARABIC = 0X00000001;

    public static final int CHAR_ENGLISH = 0X00000002;

    public static final int CHAR_CHINESE = 0X00000004;

    public static final int CHAR_OTHER_HALF = 0X00000008;

    public static int identifyCharType(char input) {
        if (input >= '0' && input <= '9') {
            return CHAR_ARABIC;
        } else if ((input >= 'a' && input <= 'z')
                || (input >= 'A' && input <= 'Z') || input == 'μ') {
            return CHAR_ENGLISH;
        } else {
            Character.UnicodeBlock ub = Character.UnicodeBlock.of(input);
            if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                    || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                    || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A) {
                //目前已知的中文字符UTF-8集合
                return CHAR_CHINESE;
            } else if (ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {    //全角数字字符和日韩字符
                return CHAR_OTHER_HALF;
            }
        }
        //其他的不做处理的字符
        return CHAR_USELESS;
    }
}
