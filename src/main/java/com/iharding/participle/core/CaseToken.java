package com.iharding.participle.core;

import com.iharding.participle.core.model.Pacs;
import com.iharding.participle.core.model.Symptom;
import com.iharding.participle.core.model.Vital;
import com.iharding.participle.util.NegativeEnum;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static com.iharding.participle.util.NegativeEnum.PACS_NAME;
import static com.iharding.participle.util.NegativeEnum.SYMPTOM;
import static com.iharding.participle.util.NegativeEnum.VITAL_NAME;

/**
 * Created by Administrator on 2017/11/9.
 */
public class CaseToken {

    private List<Symptom> symptoms = new ArrayList<Symptom>();
    private List<Vital> vitals = new ArrayList<Vital>();
    private List<Pacs> pacses = new ArrayList<Pacs>();

    public void deal(LexemePath<Lexeme> lexemePath ) {
        int i = 0;
        List<Lexeme> list = new ArrayList<>();
        for (; i < lexemePath.size(); i++) {
            if ("-1".equals(lexemePath.get(i).getFlag()) || "-2".equals(lexemePath.get(i).getFlag())) {
                this.structure(list);
                list = new ArrayList<>();
            }
            list.add(lexemePath.get(i));
        }
    }

    private void structure(List<Lexeme> list) {
        for (Lexeme lexeme : list) {
            if (StringUtils.isNotEmpty(lexeme.getProperty())) {
                if (SYMPTOM == NegativeEnum.parseOfValue(lexeme.getProperty())) {
                    this.addSymptom(list);
                    break;
                }
                if (VITAL_NAME == NegativeEnum.parseOfValue(lexeme.getProperty())) {
                    this.addVital(list);
                }
                if (PACS_NAME  == NegativeEnum.parseOfValue(lexeme.getProperty())) {
                    this.addPacs(list);
                }
            }
        }
    }

    private void addSymptom(List<Lexeme> list) {
        Symptom symptom = new Symptom();
        int i = 0;
        for (; i < list.size(); i++) {
            Lexeme lexeme = list.get(i);
            if (StringUtils.isEmpty(lexeme.getProperty())) {
                continue;
            }
            switch (NegativeEnum.parseOfValue(lexeme.getProperty())) {
                case SYMPTOM:
                    this.reflect(symptom, "symptom", lexeme.getText());
                    if (i > 0) {
                        if (list.get(i - 1).getProperty() != null && NegativeEnum.parseOfValue(list.get(i - 1).getProperty()) == NegativeEnum.FEMININE) {
                            symptom.setNegative(list.get(i - 1).getText());
                        }
                    }
                    break;
                case BODY_PART:
                    this.reflect(symptom, "bodyPart", lexeme.getText());
                    break;
                case EVENT_TIME:
                    this.reflect(symptom, "eventTime", lexeme.getText());
                    break;
                case CAUSE:
                    this.reflect(symptom, "cause", lexeme.getText());
                    break;
                case DEEP:
                    this.reflect(symptom, "deep", lexeme.getText());
                    break;
                case PROPERTY:
                    this.reflect(symptom, "property", lexeme.getText());
                    break;
                case OTHER:
                    break;
            }
        }
        for (Symptom p : symptoms) {
            if (p.getSymptom().equals(symptom.getSymptom())) {
                return;
            }
        }
        symptoms.add(symptom);
    }

    private void addVital(List<Lexeme> list) {
        Vital vital = new Vital();
        int i = 0;
        for (; i < list.size(); i++) {
            Lexeme lexeme = list.get(i);
            if (StringUtils.isEmpty(lexeme.getProperty())) {
                continue;
            }
            switch (NegativeEnum.parseOfValue(lexeme.getProperty())) {
                case VITAL_NAME:
                    this.reflect(vital, "name", lexeme.getText());
                    break;
                case VITAL_RESULT:
                    this.reflect(vital, "result", lexeme.getText());
                    if (i > 0) {
                        if (list.get(i - 1).getProperty() != null && NegativeEnum.parseOfValue(list.get(i - 1).getProperty()) == NegativeEnum.FEMININE) {
                            vital.setNegative(list.get(i - 1).getText());
                        }
                    }
                    break;
                case BODY_PART:
                    this.reflect(vital, "bodyPart", lexeme.getText());
                    break;
                case PROPERTY:
                    this.reflect(vital, "result", lexeme.getText());
                    break;
                case OTHER:
                    break;
            }
        }
        for (Vital v : vitals) {
            if (v.getName().equals(vital.getName())) {
                return;
            }
        }
        vitals.add(vital);
    }

    private void addPacs(List<Lexeme> list) {
        Pacs pacs = new Pacs();
        int i = 0;
        for (; i < list.size(); i++) {
            Lexeme lexeme = list.get(i);
            if (StringUtils.isEmpty(lexeme.getProperty())) {
                continue;
            }
            switch (NegativeEnum.parseOfValue(lexeme.getProperty())) {
                case PACS_NAME:
                    this.reflect(pacs, "name", lexeme.getText());
                    break;
                case PACS_RESULT:
                    this.reflect(pacs, "result", lexeme.getText());
                    if (i > 0) {
                        if (list.get(i - 1).getProperty() != null && NegativeEnum.parseOfValue(list.get(i - 1).getProperty()) == NegativeEnum.FEMININE) {
                            pacs.setNegative(list.get(i - 1).getText());
                        }
                    }
                    break;
                case BODY_PART:
                    this.reflect(pacs, "bodyPart", lexeme.getText());
                    break;
                case OTHER:
                    break;
            }
        }
        for (Pacs p : pacses) {
            if (p.getName().equals(pacs.getName())) {
                return;
            }
        }
        pacses.add(pacs);
    }

    private void reflect(Object obj, String method, String value) {
        try {
            Field f = obj.getClass().getDeclaredField(method);
            f.setAccessible(true);
            Object v = f.get(obj);
            if (v == null) {
                f.set(obj, value);
            } else {
                f.set(obj, v + "、" + value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Symptom> getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(List<Symptom> symptoms) {
        this.symptoms = symptoms;
    }

    public List<Vital> getVitals() {
        return vitals;
    }

    public void setVitals(List<Vital> vitals) {
        this.vitals = vitals;
    }

    public List<Pacs> getPacses() {
        return pacses;
    }

    public void setPacses(List<Pacs> pacses) {
        this.pacses = pacses;
    }
}
