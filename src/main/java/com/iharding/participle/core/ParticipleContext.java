package com.iharding.participle.core;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fyeman on 2017/10/16.
 */
public class ParticipleContext {
    private Map<Character, CHNCharacter> words;
    private Map<String, Float> ontologyLibraryMap = new HashMap<String, Float>(100, 0.75f);

    private String ontologyLibraryWord;

    private int cursor = 0;
    private int offset = 1;
    private int ontology_word_cursor = 0;
    private int chars_length = 0;

    private Map<Character, Float> rightCharMap;

    private int buffer_size = 50;
    private Character[] buffer = new Character[buffer_size];

    public ParticipleContext(Map<Character, CHNCharacter> words) {
        this.words = words;
    }

    private void init() {
        this.offset = 1;
        this.ontology_word_cursor = 0;
        this.clearBuffer();
    }

    public void start(Character[] chars) {

        if (StringUtils.join(chars, "").equals("中纵隔软组织团块")) {
            System.out.println(StringUtils.join(chars, ""));
        }
        chars_length = chars.length;
        if (chars_length == 1) return;
        this.init();
        this.cursor = 0;
        buffer[0] = chars[cursor];
        this.join(chars, chars[cursor], chars[cursor + offset]);
    }

    private void join(Character[] chars, Character current, Character next) {
        rightCharMap = words.get(current).getRight();
        if (rightCharMap.get(next) != null) {
            buffer[offset] = next;
            offset++;
            if (cursor + offset < chars_length) {
                this.join(chars, next, chars[cursor + offset]);
            } else {
                joinOntologyLibrary();
            }
        } else {
            if (offset > 1) {
                joinOntologyLibrary();
            }
            this.init();
            cursor = cursor + offset;
            buffer[0] = chars[cursor];
            if (cursor + offset < chars_length) {
                this.join(chars, chars[cursor], chars[cursor + offset]);
            }
        }
    }

    private void joinOntologyLibrary() {
        ontologyLibraryWord = StringUtils.join(buffer, "");
        if (ontologyLibraryMap.get(ontologyLibraryWord) == null) {
            ontologyLibraryMap.put(ontologyLibraryWord, 1f);
        } else {
            ontologyLibraryMap.put(ontologyLibraryWord, ontologyLibraryMap.get(ontologyLibraryWord) + 1);
        }
    }

    private void clearBuffer() {
        for (ontology_word_cursor = 0; ontology_word_cursor < buffer.length; ontology_word_cursor++) {
            buffer[ontology_word_cursor] = null;
        }
    }

    public Map<String, Float> getOntologyLibraryMap() {
        return ontologyLibraryMap;
    }

    public void setOntologyLibraryMap(Map<String, Float> ontologyLibraryMap) {
        this.ontologyLibraryMap = ontologyLibraryMap;
    }
}
