package com.iharding.participle.core;

import com.iharding.participle.util.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fyeman on 2017/10/18.
 */
public class Segment {

    private Map<Character, Segment> segment_map = new HashMap<Character, Segment>(10, 0.7f);

    private Character node_char;

    public Segment(Character node_char) {
        this.node_char = node_char;
    }

    public void fill(char[] chars, int cursor, int length) {
        node_char = Character.valueOf(chars[cursor]);
        if (Constants.chn_chars_map.get(node_char) == null) {
            Constants.chn_chars_map.put(node_char, node_char);
        }
        node_char = Constants.chn_chars_map.get(node_char);

        Segment segment = lookforSegment();
        if (segment != null) {
            if (length > 1) {
                segment.fill(chars, cursor + 1, length - 1);
            }
        }
    }

    private Segment lookforSegment() {
        Segment segment = segment_map.get(node_char);
        if (segment == null) {
            segment = new Segment(node_char);
            segment_map.put(node_char, segment);
        }
        return segment;
    }

    private Map<Character, Segment> getChildCharsMap() {
        if (segment_map == null) {
            segment_map = new HashMap<Character, Segment>(10, 0.7f);
        }
        return this.segment_map;
    }

    public Map<Character, Segment> getSegmentMap() {
        return this.segment_map;
    }
}
