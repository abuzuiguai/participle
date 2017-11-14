package com.iharding.participle.core;

import com.iharding.participle.util.CharacterUtil;

import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.Collections;

/**
 * 分词原则 1、最大长度
 *          2、相同长度频次排序
 */
public class ParticipleToken {
    private int BUFFER_SIZE = 4096;
    //每次读进预处理数据包
    private char[] buffer;
    //符号分段
    private char[] part_buffer = new char[256];

    private char[] join_symbols = new char[]{'-','/','.','*'};
    private char[] un_join_symbols = new char[]{',',':','?',';','。','，','：','；','"','“','”','\n','\r'};

    private char[] part_symbols = new char[]{'。',';','；','?','"','“','”'};
    //数据包总长度
    private int available;
    //当前位置游标，针对buffer数据包
    private int cursor = 0;
    //依据句子段落划分后游标位置
    private int offset = 0;
    private int part_start = 0;
    //词频次
    private float threshold;
    //词性
    private String property;

    private Reader reader;
    private Segment segment;
    private Segment unit_segment;

    private LexemePath<Lexeme> lexemePath;

    public ParticipleToken(Reader reader, Segment segment, Segment unit_segment) {
        this.reader = reader;
        this.segment = segment;
        this.unit_segment = unit_segment;
        Arrays.sort(join_symbols);
        Arrays.sort(un_join_symbols);
        this.lexemePath = new LexemePath<Lexeme>();

        this.read();
    }

    public void start() {
        int position = 0;
        while (hasNext()) {
            position = Arrays.binarySearch(un_join_symbols, buffer[cursor]);
            if (position > -1) {
                System.arraycopy(buffer, part_start, part_buffer, 0, cursor - part_start);
                this.next(0, cursor - part_start);

                Lexeme lexeme = new Lexeme(cursor, 1);
                char[] chars = new char[lexeme.getLength()];
                System.arraycopy(buffer, lexeme.getOffset(), chars, 0, lexeme.getLength());
                lexeme.setText(String.valueOf(chars));

                position = Arrays.binarySearch(part_symbols, buffer[cursor]);
                if (position > -1) {
                    lexeme.setFlag("-1"); //成句 '。',';','；','?'
                } else {
                    lexeme.setFlag("-2"); // ',','，',':','：'
                }
                lexemePath.add(lexeme);
                //段落分割符单独成词
//                this.addLexeme(cursor, 1);
                //下次起始位置调整到分割符之后
                part_start = cursor + 1;


                this.clear();
            }
            cursor++;
        }

        Collections.sort(lexemePath);
    }

    private void clear() {
        part_buffer = new char[256];
        this.offset = 0;
    }

    private void next(int begin, int part_length) {
        while(offset < part_length) {
            if (CharacterUtil.identifyCharType(part_buffer[offset]) == CharacterUtil.CHAR_CHINESE
                    || CharacterUtil.identifyCharType(part_buffer[offset]) == CharacterUtil.CHAR_USELESS
                    || CharacterUtil.identifyCharType(part_buffer[offset]) == CharacterUtil.CHAR_OTHER_HALF) {
                this.matchCHN(segment, offset, 0, part_length, false);
                //最大长度重新计算当前分词是否合理
                this.validate(begin, offset - begin, part_length);
            } else if (CharacterUtil.identifyCharType(part_buffer[offset]) == CharacterUtil.CHAR_ARABIC) {
                this.matchARABIC(begin, part_length);
            } else if (CharacterUtil.identifyCharType(part_buffer[offset]) == CharacterUtil.CHAR_ENGLISH) {
                this.matchARABIC(begin, part_length);
            }
            begin = offset;
        }
    }

    private void matchARABIC(int begin, int part_length) {
        int position;
        while (offset < part_length) {
            position = Arrays.binarySearch(join_symbols, part_buffer[offset]);
            if (CharacterUtil.identifyCharType(part_buffer[offset]) == CharacterUtil.CHAR_ARABIC        //数字后跟英文、'-'、'/'、'.'作为数字处理
                    || CharacterUtil.identifyCharType(part_buffer[offset]) == CharacterUtil.CHAR_ENGLISH
                    || position > -1) {
                offset ++;
            } else if (CharacterUtil.identifyCharType(part_buffer[offset]) == CharacterUtil.CHAR_CHINESE) {    //数字后跟中文单位
                this.matchCHN(unit_segment, offset, 0, part_length, true);
                break;
            } else {
                break;
            }
        }
        this.addLexeme(part_start + begin, offset - begin);
    }

    /**
     *
     * @param s
     * @param begin
     * @param length
     * @param part_length
     * @param status   递归标示，部分字打头的词在字典中有可能不存在，此时应认为该字是一个词，offset+1，进入递归方法前将值设置为true
     */
    private void matchCHN(Segment s, int begin, int length, int part_length, boolean status) {
//        if (begin == part_length) {
//            this.offset = begin;
//            return;
//        }
        Character character = Character.valueOf(part_buffer[begin]);
        Segment character_segment = s.getSegmentMap().get(character);
        if (character_segment != null) {
            status = true;
            matchCHN(character_segment, begin + 1, length + 1, part_length, status);
        } else {
            if (s.isLexeme() == false && length > 1) {//不成词进行回溯
                do {
                    s = s.parent();
                    begin--;
                    length--;
                } while (s != null && s.isLexeme() == false && length > 1);
            }
            if (!status) { //字典库不存在该字 且不是unit_segment
                this.offset++;
            } else {
                this.offset = begin;
            }
            //保存词频数据，便于最大长度相同时 按词频排序
            this.threshold = s.getThreshold();
            this.property = s.getProperty();
        }
    }

    /**
     * 分词规则：1、最大词长度匹配
     *           2、相同词长度 按频次优先
     * @param begin
     * @param length
     * @param part_length
     */
    private void validate(int begin, int length, int part_length) {
        //因为再次去按最长匹配度查找会改变offset的值，因此先保存当前offset
        int o_offset = this.offset;
        float o_threshold = this.threshold;
        int o_begin = begin;
        int max_length = length;
        int max_begin = this.offset;
        //词性会被递归覆盖，需要先保存
        String property = this.property;
        while (o_begin < o_offset - 1) {
            o_begin++;
            this.matchCHN(segment, o_begin, 0, part_length, false);
            if (this.offset - o_begin > max_length) {
                max_length = this.offset - o_begin;
                max_begin = o_begin;
                property = this.property;
            } else if (this.offset - o_begin == max_length) {
                if (this.threshold > o_threshold) {
                    max_begin = o_begin;
                    property = this.property;
                }
            }
        }
        //还原词性
        this.property = property;
        if (max_length >= length) {
            this.offset = max_begin;
        } else {
            this.offset = o_offset; //再次去最长匹配后会修改offset值，因此需要还原offset
        }
        this.addLexeme(part_start + begin, offset - begin);
    }

    private void addLexeme(int begin, int length) {
        Lexeme lexeme = new Lexeme(begin, length);
        char[] chars = new char[lexeme.getLength()];
        System.arraycopy(buffer, lexeme.getOffset(), chars, 0, lexeme.getLength());
        lexeme.setText(String.valueOf(chars));
        lexeme.setProperty(this.property);
        lexemePath.add(lexeme);
    }

    public boolean hasNext() {
        if (cursor < available) {
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

    public LexemePath<Lexeme> getLexemePath() {
        return lexemePath;
    }

    public void setLexemePath(LexemePath<Lexeme> lexemePath) {
        this.lexemePath = lexemePath;
    }
}
