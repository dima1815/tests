package com.mycomp.execspec.jiraplugin.service;

import com.atlassian.activeobjects.tx.Transactional;
import com.mycomp.execspec.jiraplugin.dto.StoryModel;

import java.util.List;

@Transactional
public interface StoryService {

    void create(StoryModel storyModel, long issueId);

    void create(StoryModel storyModel, String issueKey);

    void update(StoryModel storyModel);

    List<StoryModel> all();

    StoryModel findByIssueKey(String issueKey);

    StoryModel findById(Long storyId);

    void delete(Long storyId);
}


