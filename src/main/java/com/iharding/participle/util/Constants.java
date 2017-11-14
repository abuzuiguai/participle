package com.iharding.participle.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fyeman on 2017/9/29.
 */
public class Constants {
    public static Map<Character, Character> chn_chars_map = new HashMap<Character, Character>(100, 0.75f);

    public static final String SYMPTOM = "1";
    public static final String PAST = "2";
    public static final String OTHER = "3";
    public static final String VITAL = "4";
    public static final String LIS = "5";
    public static final String PACS = "6";
    public static final String DISEASE = "7";
}
