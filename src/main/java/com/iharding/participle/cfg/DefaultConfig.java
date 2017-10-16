package com.iharding.participle.cfg;

import com.iharding.participle.util.CharacterUtil;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fyeman on 2017/9/29.
 */
public class DefaultConfig implements Configuration {
    private static final String DEFAULT_PATH = "participle.txt";
    private List<Character[]> contents = new ArrayList<Character[]>();
    private boolean isLastCharChinese = false;

    public List<Character[]> loadAnalyticalFile(String path) {
        InputStream is = null;
        try {
            if (StringUtils.isEmpty(path)) path = DEFAULT_PATH;
            is = this.getClass().getClassLoader().getResourceAsStream(path);
            if (is == null) {
                throw new RuntimeException("词频统计文件未找到!!");
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"), 512);
            String theWord = null;
            do {
                theWord = br.readLine();
                if (theWord != null && !"".equals(theWord.trim())) {
                    cleanChars(theWord.trim().toCharArray());
                }
            } while (theWord != null);
        } catch (IOException ioe) {
            System.err.println("读取词频统计文件出错.......................");
            ioe.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                    is = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return contents;
    }

    private void cleanChars(char[] lineChars) {
        List<Character> chineseCharsList = new ArrayList<>();
        if (isLastCharChinese && CharacterUtil.identifyCharType(lineChars[0]) == CharacterUtil.CHAR_CHINESE) {
            chineseCharsList = new ArrayList<>();
            Character[] lineLastChars = contents.get(contents.size() - 1);
            for (int i = 0; i < lineLastChars.length; i++) {
                chineseCharsList.add(lineLastChars[i]);
            }
            contents.remove(contents.size() - 1);
        }
        peelChineseChars(lineChars, chineseCharsList);
    }

    private void peelChineseChars(char[] lineChars, List<Character> chineseCharsList) {
        int i = 0;
        for (i = 0; i < lineChars.length; i++) {
            if (CharacterUtil.identifyCharType(lineChars[i]) == CharacterUtil.CHAR_CHINESE) {
                chineseCharsList.add(new Character(lineChars[i]));
            } else {
                if (chineseCharsList.size() > 0) {
                    Character[] chineseChars = new Character[chineseCharsList.size()];
                    chineseCharsList.toArray(chineseChars);
                    contents.add(chineseChars);
                    chineseCharsList = new ArrayList<>();
                }
            }

            if (i == lineChars.length - 1) {
                if (CharacterUtil.identifyCharType(lineChars[i]) != CharacterUtil.CHAR_CHINESE) {
                    isLastCharChinese = false;
                } else {
                    if (chineseCharsList.size() > 0) {
                        Character[] chineseChars = new Character[chineseCharsList.size()];
                        chineseCharsList.toArray(chineseChars);
                        contents.add(chineseChars);
                    }
                    isLastCharChinese = true;
                }
            }
        }
    }
}
