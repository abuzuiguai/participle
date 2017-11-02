package com.iharding.participle.core;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fyeman on 2017/9/29.
 */
public class CHNCharacterFilter {
    private ObjectMapper mapper = new ObjectMapper();
    private double FREQUENCY_THRESHOLD = 0.01;
    private float COUNT_THRESHOLD = 100000;
    private float LESS_COUNT = 1f;

    /**
     * 设定阀值删除低于阀值的左右邻数据
     *
     * @param words
     */
    public void filter(Map<Character, CHNCharacter> words) {
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
                if (rightEntry.getValue() == null || rightCHNChar.getLeft().get(entry.getKey()) == null
                        || rightEntry.getValue() < LESS_COUNT) {
                    continue;
                }
                if (rightEntry.getValue() / CHNChar.getCount() >= FREQUENCY_THRESHOLD || rightEntry.getValue() >= COUNT_THRESHOLD
                        || rightCHNChar.getLeft().get(entry.getKey()) / rightCHNChar.getCount() >= FREQUENCY_THRESHOLD
                        || rightCHNChar.getLeft().get(entry.getKey()) >= COUNT_THRESHOLD
                        ) {
                    rightFilterCharMap.put(rightEntry.getKey(), rightEntry.getValue());
                }
            }
            CHNChar.setRight(rightFilterCharMap);
            entry.setValue(CHNChar);
        }
    }
}

