package com.iharding.participle.web;

import com.iharding.participle.cfg.Configuration;
import com.iharding.participle.cfg.DefaultConfig;
import com.iharding.participle.core.*;
import com.iharding.participle.core.model.Response;
import com.iharding.participle.jdbc.LexiconDict;
import com.iharding.participle.jdbc.LexiconFinal;
import com.iharding.participle.jdbc.MysqlJdbc;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/9.
 */
@Controller
@RequestMapping("/web")
public class ParticipleController {

    @ResponseBody
    @RequestMapping("/start")
    public Response start(String content){
        Response response = new Response();

        long start = System.currentTimeMillis();
        Configuration configuration = new DefaultConfig();
        Segment segment = configuration.loadMainDict("tc.dic");
        Segment unit_segment = configuration.loadMainDict("quantifier.dic");
        ParticipleToken token = new ParticipleToken(new StringReader(content), segment, unit_segment);
        token.start();

        MysqlJdbc mysqlJdbc = new MysqlJdbc();
        List<LexiconDict> list = mysqlJdbc.searchLexiconDict("select * from lexicon_dict order by id");
        response.setDicts(list);

        Map<String, String> map = new HashMap<String, String>();
        for (LexiconDict dict : list) {
            map.put(String.valueOf(dict.getId()), dict.getColor());
        }

        LexemePath<Lexeme> lexemePath = token.getLexemePath();
        for (Lexeme lexeme : lexemePath) {
            if (lexeme.getOffset() > -1) {
                if (lexeme.getProperty() != null && !"99".equals(lexeme.getProperty())) {
                    response.setStructure(response.getStructure() + "&nbsp;" + "<small class='label' style='background-color:" + map.get(lexeme.getProperty())
                            + "!important;white-space:normal;margin-top:5px;'>" + lexeme.getText() + "</small>");
                } else {
                    response.setStructure(response.getStructure() + "&nbsp;" + lexeme.getText());
                }
            }
        }
        response.setStructure(response.getStructure().replaceAll("\n", "</br>"));

        CaseToken caseToken = new CaseToken();
        caseToken.deal(lexemePath);

        token.end();

        long end = System.currentTimeMillis();

        System.out.println("总耗时:........." + (end - start));


        response.setSymptoms(caseToken.getSymptoms());
        response.setVitals(caseToken.getVitals());
        response.setPacses(caseToken.getPacses());
        return response;
    }
}
