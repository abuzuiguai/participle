package com.iharding.participle;

import com.iharding.participle.cfg.Configuration;
import com.iharding.participle.cfg.DefaultConfig;
import com.iharding.participle.core.Lexeme;
import com.iharding.participle.core.ParticipleToken;
import com.iharding.participle.core.Segment;
import com.iharding.participle.dijkstra.LexemePath;

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

//        ParticipleToken token = new ParticipleToken(new StringReader("鲁冠球追悼会将在万向集团公司多功能厅举行。那里是他一手缔造的商业帝国，也是他一生魂牵梦系的地方。这场告别，除了深深的不舍，还有些猝不及防，哪怕那些知道他病情的人，都会觉得太快，太突然了。和大多数员工的感受差不多，很多人都觉得鲁冠球这个名字都没怎么从视野里消失过，怎么人就没了呢？在生命的最后时光，这位商界叱咤数十年的老人在想什么？他缠绵病塌之际还在关心国家大事吗？当医生坦陈病情之时，面对生死，他是否也和平常人那样充满恐惧，对生命无比眷恋？要放手之际，面对着一手缔造的商业王国，他有没有不舍，有没有遗憾？老伴、儿子、女儿、下属、朋友，弥留之际，他对他们又做出了哪些有情有义之举？我们综合多方信息，力图还原出一代风云浙商鲁冠球在人生最后时光里的几个场景，从中，可以看出他对待生命、对待家人、对自己这一生的态度。"), segment, unit_segment);
        ParticipleToken token = new ParticipleToken(new StringReader("[病例特点]\n" +
                "1.28岁女性，0-0-0-0，末次月经2014-3-18，预产2014-12-23，因“停经39周”入院。\n" +
                "2.孕妇平素月经规则，停经1月测尿HCG（+），停经2月余查B超提示宫内单活胎。孕早期无明显 恶心、呕吐等早孕反应。定期产检，未见明显异常。孕妇今产检查B超提示羊水指数5.94cm，现无下腹痛，未见红，无阴道流液，自觉胎动稍有减少，具体未自数。\n" +
                "3.既往体健，NKA。\n" +
                "4.查体：体温(℃): 36.3，脉搏(次/分):80，呼吸(次/分):20，血压(mmHg):116/70，神清，精神可，心肺听诊无殊，骨盆外测量(cm):25-27-19.5-9.5，腹围(cm):97，宫高(cm):32，估计胎儿体重(g):3300 ，先露:头 ，衔接:半固 ，胎位:LOA ，胎心率(bpm):140，宫缩: 无，胎膜未破。\n" +
                "5.辅助检查：2014-12-16我院B超：检查结论： 1.宫内单活胎，孕约37周  2.羊水偏少。检查内容： 胎位：LOA  胎盘：前壁Ⅱ级+  双顶径(cm)：9.20  股长径(cm) ：6.45  头围（cm）：32.90  腹围（cm）：33.6  胎心率(bpm）：155  脐带：无绕颈  S/D ：1.96  胎动：可见  羊水(cm)：2.28/1.47/2.19/0（羊水指数：5.94）。\n"), segment, unit_segment);
        token.start();

        LexemePath<Lexeme> lexemePath = token.getLexemePath();
        for (Lexeme lexeme : lexemePath) {
            System.out.print(lexeme.getText() + "  ");
        }

        token.end();

        long end = System.currentTimeMillis();

        System.out.println("总耗时:........." + (end - start));
    }
}
