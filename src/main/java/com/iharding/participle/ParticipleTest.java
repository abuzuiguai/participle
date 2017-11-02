package com.iharding.participle;

import com.iharding.participle.cfg.Configuration;
import com.iharding.participle.cfg.DefaultConfig;
import com.iharding.participle.core.Lexeme;
import com.iharding.participle.core.ParticipleToken;
import com.iharding.participle.core.Segment;
import com.iharding.participle.core.LexemePath;

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
        ParticipleToken token = new ParticipleToken(new StringReader("1.患者68岁男性，因“痰中带血7月余，胸闷气急2月，加重20天。”入院。\n" +
                "2.患者7月前无明显诱因下出现咳嗽咳痰，痰中带血，血色鲜红，量少，至当地医院就诊，肺部CT提示“肺部占位”（报告未见），随后至复旦大学附属中山医院就诊，查支气管镜提示“气管见新生物浸润表现，左主气管开口、隆突侧壁见新生物浸润，左主气管口见菜花样新生物隆起导致官腔狭窄”，取活检后病理提示“鳞癌”，行全身骨ECT及头颅MR增强未见明显异常，于2014.05.08开始GP方案化疗，具体剂量为健择1.8g ivgtt once+顺铂120mg ivgtt once，期间共行7次化疗。2月前因胸闷气促入院，胸部CT提示：1.气管及左右主支气管内软组织影，中纵隔软组织团块，纵隔恶性肿瘤侵犯气管考虑，建议进一步增强及纤支镜检查。2.慢支伴感染、肺气肿征象。3.右肺小结节，请随访；两肺散在纤维灶，右肺底炎性纤维灶。排除禁忌后于2014-11-03行经内镜气管内肿瘤切除术，手术过程顺利，术后患者胸闷气急明显缓解出院。20天前患者无明显诱因出现头痛明显，伴胸闷气急，偶有咳嗽咳痰，痰白质粘，不易咳出，经针灸、服中药后无明显好转，7天前胸闷气急加重，活动受限，为进一步治疗入院。    \n" +
                "3.既往长期吸烟史，20支/日，现已戒烟7年。发现糖尿病病史6月余，现使用孚来迪0.5g tid控制血糖，未遵循糖尿病饮食，血糖控制不佳；长期咳嗽咳痰，近10年来反复，秋冬季为主，每年持续3个月以上，未行相关治疗措施。\n" +
                "4.查体:神清，精神可。皮肤巩膜无黄染，嘴唇无紫绀，颈部淋巴结未及肿大，颈静脉无明显充盈，桶状胸，气管居中。双肺触觉语颤一致，呼吸音清，可闻及明显的干啰音。腹平软，全腹无压痛反跳痛。 双下肢无水肿，病理征未引出。\n" +
                "5.辅助检查：我院 2014-10-28 胸部CT:1.气管及左右主支气管内软组织影，中纵隔软组织团块，纵隔恶性肿瘤侵犯气管考虑；2.慢支伴感染、肺气肿征象；3.右肺小结节，请随访；两肺散在纤维灶，右肺底炎性纤维灶。肺功能检查：极重度混合性通气功能障碍。\n"), segment, unit_segment);
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
