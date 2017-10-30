package com.iharding.participle;

import com.iharding.participle.cfg.Configuration;
import com.iharding.participle.cfg.DefaultConfig;
import com.iharding.participle.core.AnalyzeContext;
import com.iharding.participle.core.CHNCharacter;
import com.iharding.participle.core.CHNCharacterUtil;
import com.iharding.participle.core.ParticipleContext;
import com.iharding.participle.jdbc.MysqlJdbc;

import java.util.*;

/**
 * Created by Administrator on 2017/10/25.
 */
public class LookLexemeTest {
    public static void main(String[] args) {
                long start = System.currentTimeMillis();

        Configuration configuration = new DefaultConfig();
        List<Character[]> contents = configuration.loadAnalyticalFile("patient.txt");


        int i = 0;
        int size = contents.size();
        CHNCharacterUtil CHNCharUtil = new CHNCharacterUtil();
        for (i = 0; i < size; i++) {
//            if (i % 10000 == 0) {
//                System.out.println("开始处理第...." + i + "行");
//            }
            CHNCharUtil.fillCHNCharacter(contents.get(i));
        }

        Map<Character, CHNCharacter> words = CHNCharUtil.getWords();
        AnalyzeContext context = new AnalyzeContext();
        context.analyzeCHNCharacter(words);

        ParticipleContext participleContext = new ParticipleContext(words);
        for (i = 0; i < size; i++) {
//            if (i % 10000 == 0) {
//                System.out.println("本体库创建...." + i + "行");
//            }
            participleContext.start(contents.get(i));
        }

        List<Map.Entry<String, Float>> ontologyLibraryList = new ArrayList<Map.Entry<String, Float>>(participleContext.getOntologyLibraryMap().entrySet());
        Collections.sort(ontologyLibraryList, new Comparator<Map.Entry<String, Float>>() {
            public int compare(Map.Entry<String, Float> o1, Map.Entry<String, Float> o2) {
                return (o1.getKey()).toString().compareTo(o2.getKey());
            }
        });

        MysqlJdbc mysql = new MysqlJdbc();
        mysql.save(ontologyLibraryList);
        for (Map.Entry<String, Float> entry : ontologyLibraryList) {
            if (entry.getValue() > 10) {
                System.out.println(entry.getKey());
            }
        }

        long end = System.currentTimeMillis();

        System.out.println("总耗时:........." + (end - start));
    }
}
