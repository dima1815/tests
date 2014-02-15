package com.mycomp.execspec.jiraplugin.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * Created by Dmytro on 2/8/14.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ScenarioModel {

    private Integer id;

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ScenarioModel{" +
                "id=" + id +
                ", text='" + text + '\'' +
                '}';
    }
}
