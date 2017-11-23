package com.iharding.participle.jdbc;

import java.sql.*;
import java.util.*;

/**
 * Created by Administrator on 2017/10/25.
 */
public class MysqlJdbc {

    public Connection connect() {
        Connection conn = null;
        String url = "jdbc:mysql://localhost:3306/nlp?"
                + "user=root&password=langtong&useUnicode=true&characterEncoding=UTF8";
        try {
            // 之所以要使用下面这条语句，是因为要使用MySQL的驱动，所以我们要把它驱动起来，
            // 可以通过Class.forName把它加载进去，也可以通过初始化来驱动起来，下面三种形式都可以
            Class.forName("com.mysql.jdbc.Driver");// 动态加载mysql驱动
            conn = DriverManager.getConnection(url);
        } catch(SQLException e) {
            System.out.println("MySQL操作错误");
            e.printStackTrace();
        } catch (ClassNotFoundException cnfe) {
            System.out.println("未找到驱动");
            cnfe.printStackTrace();
        }
        return conn;
    }

    public List<LexiconFinal> search(String sql) {
        Connection conn = connect();
        if (conn == null) return null;

        List<LexiconFinal> list = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(sql);
            while (result.next()) {
                LexiconFinal lexiconFinal = new LexiconFinal();
                lexiconFinal.setName(result.getString(2));
                lexiconFinal.setThreshold(result.getFloat(3));
                lexiconFinal.setNegative(result.getString(5));
                list.add(lexiconFinal);
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        this.close(conn);
        return list;
    }

    public List<LexiconDict> searchLexiconDict(String sql) {
        Connection conn = connect();
        if (conn == null) return null;

        List<LexiconDict> list = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(sql);
            while (result.next()) {
                LexiconDict lexiconDict = new LexiconDict();
                lexiconDict.setId(result.getInt(1));
                lexiconDict.setName(result.getString(2));
                lexiconDict.setColor(result.getString(3));
                lexiconDict.setRemark(result.getString(4));
                list.add(lexiconDict);
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        this.close(conn);
        return list;
    }

    public void save(List<Map.Entry<String, Float>> list) {
        Connection conn = connect();
        if (conn == null) return;
        String sql = "insert into lexicon_info(name, threshold) values (?,?)";
        try {
            Statement stmt = conn.createStatement();
            for (Map.Entry<String, Float> entry : list) {
                if (entry.getValue() > 10) {
                    sql = "insert into lexicon_info(name, threshold) values ('" + entry.getKey() + "'," + entry.getValue() + ")";
                    stmt.executeUpdate(sql);
                }
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        this.close(conn);
    }

    public void save(String sql) {
        Connection conn = connect();
        if (conn == null) return;
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        this.close(conn);
    }

    public void close(Connection conn) {
        try  {
            conn.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }
}
