package com.iharding.participle.mongo;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import java.util.List;

public class MongoConnector {

    static String DBName;
    static List<ServerAddress> serveradd;
    static int PORT;

    public MongoConnector(List<ServerAddress> serverAddress, String db){
        serveradd = serverAddress;
        DBName = db;
    }

    public MongoClient getMongoClient( ){
        MongoClient mongoClient = null;
        try {
            // 连接到 mongodb 服务
            mongoClient = new MongoClient(serveradd);
            System.out.println("Connect to mongodb successfully");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return mongoClient;
    }

    public MongoDatabase getMongoDataBase(MongoClient mongoClient) {
        MongoDatabase mongoDataBase = null;
        try {
            if (mongoClient != null) {
                // 连接到数据库
                mongoDataBase = mongoClient.getDatabase(DBName);
                System.out.println("Connect to DataBase successfully");
            } else {
                throw new RuntimeException("MongoClient 不能够为空");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mongoDataBase;
    }

    public void closeMongoClient(MongoDatabase mongoDataBase,MongoClient mongoClient ) {
        if (mongoDataBase != null) {
            mongoDataBase = null;
        }
        if (mongoClient != null) {
            mongoClient.close();
        }
        System.out.println("Close MongoClient successfully");

    }
}