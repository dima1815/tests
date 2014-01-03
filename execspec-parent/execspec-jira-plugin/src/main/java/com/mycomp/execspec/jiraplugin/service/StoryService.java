package com.mycomp.execspec.jiraplugin.service;

import com.atlassian.activeobjects.tx.Transactional;
import com.mycomp.execspec.common.dto.StoryModel;

import java.util.List;

@Transactional
public interface StoryService {

    StoryModel create(StoryModel storyModel, long issueId);

    StoryModel create(StoryModel storyModel, String issueKey);

    StoryModel update(StoryModel storyModel);

    List<StoryModel> all();

    List<StoryModel> findByIssueKey(String issueKey);

    StoryModel findById(Long storyId);

    void delete(Long storyId);
}


