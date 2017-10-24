package com.iharding.participle.core;

import java.io.IOException;
import java.io.Reader;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * Created by Administrator on 2017/10/23.
 */
public class ParticipleToken {
    private int BUFFER_SIZE = 4096;

    private char[] buffer;

    private int length = 0;
    private int start = 0;
    private int available;

    private Segment segment;

    private Reader reader;

    private char[] hitedChar;

    private LinkedList<Hit> hits;

    public ParticipleToken(Reader reader, Segment segment) {
        this.reader = reader;
        this.segment = segment;
        this.read();
    }

    public Lexeme next() {
        hits = new LinkedList<Hit>();
        //读取最大文本
        this.match(segment, this.start, 0);
        //分割出来的词不一定是最大匹配 如"一中华人民共和国" 合理的分词应该是"一|中华人民共和国"而不是"一中|华人|民|共和国"
        //从分割出来的词的第二、三...继续读取，是否有长度超过已分割出来的词，如果存在则重新切分
        validate();

        hitedChar = new char[this.length];
        System.arraycopy(buffer, this.start, hitedChar, 0, this.length);

        System.out.println(String.valueOf(hitedChar));
        Lexeme lexeme = new Lexeme(this.start, this.length);
        start = this.start + this.length;
        this.length = 0;
        return lexeme;
    }

    private void validate() {
        Hit hit = hits.get(0);
        int tmpLength = hit.getLength();
        while (tmpLength > 1) {
            tmpLength--;
            this.match(segment, hit.getEnd() - tmpLength, 0);
        }

        Collections.sort(hits, new HitComparator());
        if (hits.get(0).getLength() > hit.getLength()) {
            this.length = hits.get(0).getEnd() - hits.get(0).getLength() - this.start;
        } else {
            this.length = hits.get(0).getEnd() - this.start;
        }
    }

    private void match(Segment s, int end, int length) {
        Character character = Character.valueOf(buffer[end]);
        Segment character_segment = s.getSegmentMap().get(character);
        if (character_segment != null) {
            match(character_segment, end + 1,length + 1);
        } else {
            Hit hit = new Hit(end, length);
            hits.add(hit);
        }
    }

    public boolean hasNext() {
        if (start < available) {
            return true;
        }
        return false;
    }

    private void read() {
        try {
            if (buffer == null) {
                buffer = new char[BUFFER_SIZE];
                available = reader.read(buffer);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void end() {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    class HitComparator implements Comparator<Hit> {
        @Override
        public int compare(Hit h1, Hit h2 ) {
            return (h2.getLength() - h1.getLength());
        }
    }
}
