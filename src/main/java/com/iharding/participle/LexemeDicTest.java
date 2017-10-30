package com.iharding.participle;

import com.iharding.participle.jdbc.MysqlJdbc;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/30.
 */
public class LexemeDicTest {
    public static void main(String[] args) {
        LexemeDicTest test = new LexemeDicTest();
        test.write();
    }

    private void write() {
        String sql = "select * from lexicon_info_final where status = 1 order by name";
        MysqlJdbc mysqlJdbc = new MysqlJdbc();
        List<Map.Entry<String, Float>> list = mysqlJdbc.search(sql);
        try {
            FileWriter fw = new FileWriter("E:\\git\\participle\\src\\main\\resources\\tc.dic");
            for (Map.Entry<String, Float> entry : list) {
                fw.write(entry.getKey() + "|" + entry.getValue() + "\n");
            }
            fw.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
