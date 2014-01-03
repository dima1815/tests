package com.mycomp.execspec.common.domain;

public interface Story {

    int getID();

    String getIssueKey();

    void setIssueKey(String issueKey);

    String getMeta();

    void setMeta(String storyText);

    String getNarrative();

    void setNarrative(String narrative);

    String getLastRunStatus();

    void setLastRunStatus(String lastRunStatus);
}
