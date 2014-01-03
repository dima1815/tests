package com.mycomp.execspec.jiraplugin.ao;


import java.util.List;

public interface StoryDao {

    StoryAO create();

    StoryAO read(int storyId);

    void delete(StoryAO story);

    List<StoryAO> findAll();

    List<StoryAO> findByIssueKey(String issueKey);
}


