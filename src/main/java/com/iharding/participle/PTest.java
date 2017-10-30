package com.iharding.participle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iharding.participle.cfg.Configuration;
import com.iharding.participle.cfg.DefaultConfig;
import com.iharding.participle.core.*;
import com.iharding.participle.mongo.MongoConnector;
import com.iharding.participle.mongo.MongoDao;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.FileWriter;
import java.io.StringReader;
import java.util.*;

/**
 * Created by fyeman on 2017/9/29.
 */
public class PTest {
    public static void main(String[] args) {
//        long start = System.currentTimeMillis();
//
//        Configuration configuration = new DefaultConfig();
//        List<Character[]> contents = configuration.loadAnalyticalFile("patient.txt");
//
//
//        int i = 0;
//        int size = contents.size();
//        CHNCharacterUtil CHNCharUtil = new CHNCharacterUtil();
//        for (i = 0; i < size; i++) {
//            if (i % 10000 == 0) {
//                System.out.println("开始处理第...." + i + "行");
//            }

//            System.out.println(StringUtils.join(contents.get(i), ""));
//            CHNCharUtil.fillCHNCharacter(contents.get(i));
//        }
//
//        Map<Character, CHNCharacter> words = CHNCharUtil.getWords();
//        AnalyzeContext context = new AnalyzeContext();
//        context.analyzeCHNCharacter(words);
//
//        ParticipleContext participleContext = new ParticipleContext(words);
//        for (i = 0; i < size; i++) {
//            if (i % 10000 == 0) {
//                System.out.println("本体库创建...." + i + "行");
//            }
//            participleContext.start(contents.get(i));
//        }

//        List<Map.Entry<String, Float>> ontologyLibraryList = new ArrayList<Map.Entry<String, Float>>(participleContext.getOntologyLibraryMap().entrySet());
//        Collections.sort(ontologyLibraryList, new Comparator<Map.Entry<String, Float>>() {
//            public int compare(Map.Entry<String, Float> o1, Map.Entry<String, Float> o2) {
//                return (o1.getKey()).toString().compareTo(o2.getKey());
//            }
//        });
//
//        for (Map.Entry<String, Float> entry : ontologyLibraryList) {
//            if (entry.getValue() > 10) {
//                System.out.println(entry.getKey());
//            }
//        }

//        PTest test = new PTest();
//        test.loadFromMongo();

//        long end = System.currentTimeMillis();
//
//        System.out.println("总耗时:........." + (end - start));

        PTest pTest = new PTest();
//        pTest.participle();
    }

    public void loadFromDict() {
        long start = System.currentTimeMillis();

        Configuration configuration = new DefaultConfig();
        configuration.loadMainDict("tc.dict");

        long end = System.currentTimeMillis();

        System.out.println("总耗时:........." + (end - start));
    }



    public void loadFromMongo() {
        List<ServerAddress> addresses = new ArrayList<ServerAddress>();
        addresses.add(new ServerAddress("192.168.2.190", 27020));
        MongoConnector connector = new MongoConnector(addresses, "patient");

        MongoDatabase db = connector.getMongoDataBase(connector.getMongoClient());
        MongoDao dao = new MongoDao();

        try {
            FileWriter fw = new FileWriter("c:/patient.txt");
            List<Map<String, Integer>> results = dao.queryAll(db, "inpatient");
            for (Map<String, Integer> map : results) {
                fw.write(String.valueOf(map.get("precode")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
