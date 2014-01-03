package com.mycomp.execspec.common.dto;


import com.mycomp.execspec.common.domain.Story;

/**
 * DTO class for Story instances.
 *
 * @author stasyukd
 * @since 2.0.0-SNAPSHOT
 */
public class StoryModel {

    private int id;

    private String issueKey;

    private String meta;

    private String narrative;

    private String lastRunStatus;

    public StoryModel() {
    }

    public StoryModel(Story story) {
        id = story.getID();
        issueKey = story.getIssueKey();
        meta = story.getMeta();
        narrative = story.getNarrative();
        lastRunStatus = story.getLastRunStatus();
    }

    public String getIssueKey() {
        return issueKey;
    }

    public void setIssueKey(String issueKey) {
        this.issueKey = issueKey;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public String getNarrative() {
        return narrative;
    }

    public void setNarrative(String narrative) {
        this.narrative = narrative;
    }

    public String getLastRunStatus() {
        return lastRunStatus;
    }

    public void setLastRunStatus(String lastRunStatus) {
        this.lastRunStatus = lastRunStatus;
    }

    @Override
    public String toString() {
        return "StoryModel{" +
                "issueKey='" + issueKey + '\'' +
                ", id=" + id +
                ", meta='" + meta + '\'' +
                ", narrative='" + narrative + '\'' +
                ", lastRunStatus=" + lastRunStatus +
                '}';
    }
}
