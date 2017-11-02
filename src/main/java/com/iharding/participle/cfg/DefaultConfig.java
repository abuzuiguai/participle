package com.iharding.participle.cfg;

import com.iharding.participle.core.Segment;
import com.iharding.participle.util.CharacterUtil;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by fyeman on 2017/9/29.
 */
public class DefaultConfig implements Configuration {
    private static final String DEFAULT_PATH = "patient.txt";
    private List<Character[]> contents = new ArrayList<Character[]>();

    private Map<Integer, Character> chars = new HashMap<Integer, Character>(100, 0.75f);
    private Integer charsKey;

    Segment segment;

    public List<Character[]> loadAnalyticalFile(String path) {
        List<String> fileContents = readFileContents(path);
        int i = 0;
        int length = fileContents.size();
        for (i = 0; i < length; i++) {
//            if (i > 0 && i % 1000 == 0) {
//                break;
//            }
            cleanChars(fileContents.get(i).toCharArray());
        }
        return contents;
    }

    public Segment loadMainDict(String path) {
        List<String> fileContents = readFileContents(path);
        int i = 0;
        int length = fileContents.size();

        segment = new Segment(Character.valueOf('0'));
        String[] line_string;
        char[] chars;
        for (i = 0; i < length; i++) {
            line_string = org.apache.commons.lang3.StringUtils.split(fileContents.get(i), "\\|");
            chars = line_string[0].toCharArray();
            if (line_string.length > 1) {
                segment.fill(segment, chars, 0, chars.length, Float.parseFloat(line_string[1]));
            } else {
                segment.fill(segment, chars, 0, chars.length);
            }
        }
        return segment;
    }

    public List<String> readFileContents(String path) {
        InputStream is = null;
        List<String> fileContents = new ArrayList<String>(10);
        try {
            if (StringUtils.isEmpty(path)) path = DEFAULT_PATH;
            is = this.getClass().getClassLoader().getResourceAsStream(path);
            if (is == null) {
                throw new RuntimeException(path + ".......文件未找到!!");
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"), 512);

            String theWord = null;
            do {
                theWord = br.readLine();
                if (theWord != null && !"".equals(theWord.trim())) {
                    fileContents.add(theWord.trim());
                }
            } while (theWord != null);
        } catch (IOException ioe) {
            System.err.println("读取文件" + path + "出错.......................");
            ioe.printStackTrace();
        } finally {
            this.close(is);
        }
        return fileContents;
    }

    private void close(InputStream is) {
        try {
            if (is != null) {
                is.close();
                is = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cleanChars(char[] lineChars) {
        List<Character> chineseCharsList = new ArrayList<>();
        peelChineseChars(lineChars, chineseCharsList);
    }

    /**
     * 截取中文语句
     *
     * @param lineChars
     * @param chineseCharsList
     */
    private void peelChineseChars(char[] lineChars, List<Character> chineseCharsList) {
        int i = 0;
        for (i = 0; i < lineChars.length; i++) {
            if (CharacterUtil.identifyCharType(lineChars[i]) == CharacterUtil.CHAR_CHINESE) {
                charsKey = Integer.valueOf(lineChars[i]);
                if (chars.get(charsKey) == null) {
                    chars.put(charsKey, new Character(lineChars[i]));
                }
                chineseCharsList.add(chars.get(charsKey));
            } else {
                if (chineseCharsList.size() > 0) {
                    this.addChineseChars(chineseCharsList);
                    chineseCharsList = new ArrayList<>();
                }
            }
            if (i == lineChars.length - 1) {
                if (chineseCharsList.size() > 0) {
                    this.addChineseChars(chineseCharsList);
                }
            }
        }
    }

    private void addChineseChars(List<Character> chineseCharsList) {
        Character[] chineseChars = new Character[chineseCharsList.size()];
        chineseCharsList.toArray(chineseChars);
        contents.add(chineseChars);
    }
}
