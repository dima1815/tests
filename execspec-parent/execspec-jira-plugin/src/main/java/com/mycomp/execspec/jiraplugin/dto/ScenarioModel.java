package com.mycomp.execspec.jiraplugin.dto;

import com.mycomp.execspec.jiraplugin.ao.Scenario;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * Created by Dmytro on 2/8/14.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ScenarioModel {

    private Integer id;

    private String text;

    protected ScenarioModel() {
    }

    public ScenarioModel(Scenario storyScenario) {
        this.id = storyScenario.getID();
        this.text = storyScenario.getText();
    }

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
}
