package com.mycomp.execspec.jiraplugin.service;

import com.atlassian.activeobjects.tx.Transactional;
import com.mycomp.execspec.jiraplugin.dto.input.SaveStoryModel;
import com.mycomp.execspec.jiraplugin.dto.output.StoryModel;

import java.util.List;

@Transactional
public interface StoryService {

    void create(SaveStoryModel storyModel);

    void update(SaveStoryModel storyModel);

    List<StoryModel> all();

    StoryModel findByProjectAndIssueKey(String projectKey, String issueKey);

    StoryModel findById(Long storyId);

    void delete(Long storyId);

    void delete(String projectKey, String issueKey);
}


