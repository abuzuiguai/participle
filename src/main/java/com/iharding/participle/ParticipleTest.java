package com.iharding.participle;

import com.iharding.participle.cfg.Configuration;
import com.iharding.participle.cfg.DefaultConfig;
import com.iharding.participle.core.*;
import com.iharding.participle.jdbc.MysqlJdbc;

import java.io.StringReader;

/**
 * Created by Administrator on 2017/10/27.
 */
public class ParticipleTest {
    public static void main(String[] args) {
        ParticipleTest test = new ParticipleTest();
        test.participle();
    }

    public void participle() {
        long start = System.currentTimeMillis();

        Configuration configuration = new DefaultConfig();
        Segment segment = configuration.loadMainDict("tc.dic");

        Segment unit_segment = configuration.loadMainDict("quantifier.dic");

        ParticipleToken token = new ParticipleToken(new StringReader("双下肢水肿了，双下肢无水肿了"), segment, unit_segment);
        token.start();

        MysqlJdbc mysqlJdbc = new MysqlJdbc();
        String sql = "";

        LexemePath<Lexeme> lexemePath = token.getLexemePath();
        for (Lexeme lexeme : lexemePath) {
            if (lexeme.getOffset() > -1) {
                System.out.print(lexeme.getText() + " ");
                sql = "insert into lexicon_result(name) values ('" + lexeme.getText() + "')";
//            mysqlJdbc.save(sql);
            }
        }

        CaseToken caseToken = new CaseToken();
        caseToken.deal(lexemePath);

        token.end();

        long end = System.currentTimeMillis();

        System.out.println("总耗时:........." + (end - start));
    }


}
