package com.iharding.participle.core;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fyeman on 2017/10/16.
 */
public class CHNCharacter {
    private float count = 0f;
    private Map<Character, Float> left = new HashMap<Character, Float>(18, 0.75f);
    private Map<Character, Float> right = new HashMap<Character, Float>(18, 0.75f);

    public Map<Character, Float> getLeft() {
        return left;
    }

    public void setLeft(Map<Character, Float> left) {
        this.left = left;
    }

    public Map<Character, Float> getRight() {
        return right;
    }

    public void setRight(Map<Character, Float> right) {
        this.right = right;
    }

    public float getCount() {
        return count;
    }

    public void setCount(Float count) {
        this.count = count;
    }
}
