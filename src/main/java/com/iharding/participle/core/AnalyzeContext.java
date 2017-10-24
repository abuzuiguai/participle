package com.iharding.participle.core;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fyeman on 2017/9/29.
 */
public class AnalyzeContext {
    private ObjectMapper mapper = new ObjectMapper();
    private double FREQUENCY_THRESHOLD = 0.1;
    private float COUNT_THRESHOLD = 100;
    private float LESS_COUNT = 3f;

    public void analyzeCHNCharacter(Map<Character, CHNCharacter> words) {
        CHNCharacter CHNChar;
        CHNCharacter rightCHNChar;
        Map<Character, Float> rightCharMap;
        Map<Character, Float> rightFilterCharMap;
        for (Map.Entry<Character, CHNCharacter> entry : words.entrySet()) {
            CHNChar = entry.getValue();
            rightCharMap = CHNChar.getRight();
            rightFilterCharMap = new HashMap<Character, Float>(100, 0.75f);
            for (Map.Entry<Character, Float> rightEntry : rightCharMap.entrySet()) {
                rightCHNChar = words.get(rightEntry.getKey());
                if (rightEntry.getValue() == null || rightCHNChar.getLeft().get(entry.getKey()) == null || rightEntry.getValue() < LESS_COUNT) {
                    continue;
                }
                if (entry.getKey() == '下' && rightEntry.getKey() == '胀') {
                    System.out.println("下" + CHNChar.getCount() + "   胀" + rightEntry.getValue());
                }
                if (rightEntry.getValue() / CHNChar.getCount() >= FREQUENCY_THRESHOLD || rightEntry.getValue() >= COUNT_THRESHOLD
                        || rightCHNChar.getLeft().get(entry.getKey()) / rightCHNChar.getCount() >= FREQUENCY_THRESHOLD || rightCHNChar.getLeft().get(entry.getKey()) >= COUNT_THRESHOLD
                        ) {
                    rightFilterCharMap.put(rightEntry.getKey(), rightEntry.getValue());
                }
            }
            CHNChar.setRight(rightFilterCharMap);
            entry.setValue(CHNChar);
            try {
//                System.out.println(String.valueOf(entry.getKey() + ":") + mapper.writeValueAsString(entry.getValue().getRight()));
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
