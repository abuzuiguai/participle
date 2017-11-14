package com.iharding.participle.web;

import com.iharding.participle.cfg.Configuration;
import com.iharding.participle.cfg.DefaultConfig;
import com.iharding.participle.core.*;
import com.iharding.participle.core.model.Response;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.StringReader;

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

        LexemePath<Lexeme> lexemePath = token.getLexemePath();

        for (Lexeme lexeme : lexemePath) {
            if (lexeme.getOffset() > -1) {
                response.setStructure(response.getStructure() + "&nbsp;&nbsp;" + lexeme.getText());
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
