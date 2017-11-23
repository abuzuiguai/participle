package com.iharding.participle.core.model;

import com.iharding.participle.jdbc.LexiconDict;

import java.util.List;

/**
 * Created by Administrator on 2017/11/9.
 */
public class Response {
    private String structure = "";

    private List<LexiconDict> dicts;

    private List<Symptom> symptoms;
    private List<Vital> vitals;
    private List<Pacs> pacses;

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

    public String getStructure() {
        return structure;
    }

    public void setStructure(String structure) {
        this.structure = structure;
    }

    public List<Pacs> getPacses() {
        return pacses;
    }

    public void setPacses(List<Pacs> pacses) {
        this.pacses = pacses;
    }

    public List<LexiconDict> getDicts() {
        return dicts;
    }

    public void setDicts(List<LexiconDict> dicts) {
        this.dicts = dicts;
    }
}
