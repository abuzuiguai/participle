package com.iharding.participle;

import com.iharding.participle.jdbc.LexiconDict;
import com.iharding.participle.jdbc.LexiconFinal;
import com.iharding.participle.jdbc.MysqlJdbc;
import org.springframework.util.StringUtils;

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
        test.writeToFinal();
//        test.writeToQuantifier();
        test.writeToLexemeProperty();
    }

    private void writeToFinal() {
        String sql = "select * from lexicon_info_final where status = 1 order by name";
        MysqlJdbc mysqlJdbc = new MysqlJdbc();
        List<LexiconFinal> list = mysqlJdbc.search(sql);
        try {
            FileWriter fw = new FileWriter("E:\\git\\participle\\src\\main\\resources\\tc.dic");
            for (LexiconFinal lexiconFinal : list) {
                if (StringUtils.isEmpty(lexiconFinal.getNegative())) {
                    lexiconFinal.setNegative("99");
                }
                fw.write(lexiconFinal.getName() + "|" + lexiconFinal.getThreshold() + "|" + lexiconFinal.getNegative() + "\n");
            }
            fw.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void writeToQuantifier() {
        String sql = "select * from lexicon_info_quantifier where status = 1 order by name";
        MysqlJdbc mysqlJdbc = new MysqlJdbc();
        List<LexiconFinal> list = mysqlJdbc.search(sql);
        try {
            FileWriter fw = new FileWriter("E:\\git\\participle\\src\\main\\resources\\quantifier.dic");
            for (LexiconFinal lexiconFinal : list) {
                if (StringUtils.isEmpty(lexiconFinal.getNegative())) {
                    lexiconFinal.setNegative("99");
                }
                fw.write(lexiconFinal.getName() + "|" + lexiconFinal.getThreshold() + "|" + lexiconFinal.getNegative() + "\n");
            }
            fw.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void writeToLexemeProperty() {
        String sql = "select * from lexicon_dict order by id";
        MysqlJdbc mysqlJdbc = new MysqlJdbc();
        List<LexiconDict> list = mysqlJdbc.searchLexiconDict(sql);
        try {
            FileWriter fw = new FileWriter("E:\\git\\participle\\src\\main\\resources\\lexeme_property.dic");
            for (LexiconDict lexiconDict : list) {
                fw.write(lexiconDict.getId() + "|" + lexiconDict.getName() + "|" + lexiconDict.getColor() + "\n");
            }
            fw.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
