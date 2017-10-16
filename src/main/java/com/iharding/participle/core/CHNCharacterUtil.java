package com.iharding.participle.core;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fyeman on 2017/10/16.
 */
public class CHNCharacterUtil {
    private Map<Character, CHNCharacter> words = new HashMap<>(100, 0.75f);
    private long word_length = 0;
    private int cursor = 0;

    private Map<Character, Float> leftCharMap;
    private Map<Character, Float> rightCharMap;
    private Float leftCharCount;
    private Float rightCharCount;

    private Character cursorChar;
    private Character leftChar;
    private Character rightChar;

    public Map<Character, CHNCharacter> fillCHNCharacter(Character[] chars) {
        word_length = chars.length;
        CHNCharacter CHNChar;
        for (cursor = 0; cursor < word_length; cursor++) {
            cursorChar = chars[cursor];
            if (words.get(cursorChar) == null) { //新字符
                words.put(cursorChar, new CHNCharacter());
            }
            CHNChar = words.get(cursorChar);
            if (cursor == 0 && cursor < word_length - 1) {
                CHNChar = updateRightChar(chars, CHNChar);
            } else if (cursor > 0 && cursor == word_length - 1) {
                CHNChar = updateLeftChar(chars, CHNChar);
            } else if (cursor > 0 && cursor < word_length - 1) {
                updateLeftChar(chars, CHNChar);
                updateRightChar(chars, CHNChar);
            }
            CHNChar.setCount(CHNChar.getCount() + 1);
            words.put(cursorChar, CHNChar);
        }
        return words;
    }

    private CHNCharacter updateLeftChar(Character[] chars, CHNCharacter CHNChar) {
        leftCharMap = CHNChar.getLeft();
        leftChar = chars[cursor - 1];
        leftCharCount = leftCharMap.get(leftChar);
        if (leftCharCount == null) {
            leftCharMap.put(leftChar, 1f);
        } else {
            leftCharMap.put(leftChar, leftCharCount + 1);
        }
        CHNChar.setLeft(leftCharMap);
        return CHNChar;
    }

    private CHNCharacter updateRightChar(Character[] chars, CHNCharacter CHNChar) {
        rightCharMap = CHNChar.getRight();
        rightChar = chars[cursor + 1];
        rightCharCount = rightCharMap.get(rightChar);
        if (rightCharCount == null) {
            rightCharMap.put(rightChar, 1f);
        } else {
            rightCharMap.put(rightChar, rightCharCount + 1);
        }
        CHNChar.setRight(rightCharMap);
        return CHNChar;
    }

    public Map<Character, CHNCharacter> getWords() {
        return words;
    }

    public void setWords(Map<Character, CHNCharacter> words) {
        this.words = words;
    }
}
