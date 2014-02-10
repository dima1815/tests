package com.mycomp.execspec.jiraplugin.dto;


import com.mycomp.execspec.jiraplugin.ao.Scenario;
import com.mycomp.execspec.jiraplugin.ao.Story;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO class for Story instances.
 *
 * @author stasyukd
 * @since 2.0.0-SNAPSHOT
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class StoryModel {

    private Integer id;

    private String issueKey;

    private String narrative;

    private List<ScenarioModel> scenarios;

    public StoryModel() {
    }

    public StoryModel(Story story) {
        id = story.getID();
        issueKey = story.getIssueKey();
        narrative = story.getNarrative();
        Scenario[] storyScenarios = story.getScenarios();
        scenarios = new ArrayList<ScenarioModel>(storyScenarios.length);
        for (Scenario storyScenario : storyScenarios) {
            ScenarioModel sm = new ScenarioModel(storyScenario);
            scenarios.add(sm);
        }
    }

    public String getIssueKey() {
        return issueKey;
    }

    public void setIssueKey(String issueKey) {
        this.issueKey = issueKey;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNarrative() {
        return narrative;
    }

    public void setNarrative(String narrative) {
        this.narrative = narrative;
    }

    public List<ScenarioModel> getScenarios() {
        return scenarios;
    }

    public void setScenarios(List<ScenarioModel> scenarios) {
        this.scenarios = scenarios;
    }

    @Override
    public String toString() {
        return "StoryModel{" +
                "id=" + id +
                ", issueKey='" + issueKey + '\'' +
                ", narrative='" + narrative + '\'' +
                ", scenarios=" + scenarios +
                '}';
    }
}
