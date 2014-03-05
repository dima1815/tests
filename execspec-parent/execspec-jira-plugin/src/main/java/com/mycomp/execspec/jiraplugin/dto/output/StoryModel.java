package com.mycomp.execspec.jiraplugin.dto.output;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
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

    private final String issueKey;

    private final String projectKey;

    private final NarrativeModel narrative;

    private final List<ScenarioModel> scenarios;

    public StoryModel(String issueKey, String projectKey, NarrativeModel narrative, List<ScenarioModel> scenarios) {
        this.issueKey = issueKey;
        this.projectKey = projectKey;
        this.narrative = narrative;
        this.scenarios = scenarios;
    }

    public String getIssueKey() {
        return issueKey;
    }

    public String getProjectKey() {
        return projectKey;
    }

    public NarrativeModel getNarrative() {
        return narrative;
    }

    public List<ScenarioModel> getScenarios() {
        return scenarios;
    }
}
