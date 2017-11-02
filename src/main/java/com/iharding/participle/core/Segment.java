package com.iharding.participle.core;

import com.iharding.participle.util.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 词典树
 */
public class Segment {

    private Map<Character, Segment> segment_map = new HashMap<Character, Segment>(10, 0.7f);

    private Character node_char;

    private boolean isLexeme = false;
    //频次
    private float threshold = 0f;

    private Segment parent;

    public Segment(Character node_char) {
        this.node_char = node_char;
    }

    public void fill(Segment s, char[] chars, int cursor, int length) {
        this.fill(s, chars, cursor, length, 0f);
    }

    public void fill(Segment s, char[] chars, int cursor, int length, float threshold) {
        Character c_char = Character.valueOf(chars[cursor]);
        if (Constants.chn_chars_map.get(c_char) == null) {
            Constants.chn_chars_map.put(c_char, c_char);
        }
        c_char = Constants.chn_chars_map.get(c_char);

        Segment segment = lookforSegment(c_char);
        if (segment != null) {
            segment.setParent(s);
            if (length > 1) {
                segment.fill(segment, chars, cursor + 1, length - 1, threshold);
            } else {
                segment.isLexeme = true;
                segment.threshold = threshold;
                segment_map.put(c_char, segment);
            }
        }
    }

    private Segment lookforSegment(Character c_char) {
        Segment segment = segment_map.get(c_char);
        if (segment == null) {
            segment = new Segment(c_char);
            segment_map.put(c_char, segment);
        }
        return segment;
    }

    private Map<Character, Segment> getChildCharsMap() {
        if (segment_map == null) {
            segment_map = new HashMap<Character, Segment>(10, 0.7f);
        }
        return this.segment_map;
    }

    public boolean isLexeme() {
        return this.isLexeme;
    }

    public void setParent(Segment segment) {
        this.parent = segment;
    }

    public Segment parent() {
        return this.parent;
    }

    public Map<Character, Segment> getSegmentMap() {
        return this.segment_map;
    }

    public float getThreshold() {
        return threshold;
    }

    public void setThreshold(float threshold) {
        this.threshold = threshold;
    }
}
