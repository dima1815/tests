package com.mycomp.execspec.jiraplugin.service;

import com.atlassian.crowd.embedded.api.User;
import com.atlassian.jira.bc.issue.IssueService;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.mycomp.execspec.common.dto.ModelUtils;
import com.mycomp.execspec.common.dto.StoryModel;
import com.mycomp.execspec.jiraplugin.ao.StoryDao;
import com.mycomp.execspec.jiraplugin.ao.StoryAO;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public final class StoryServiceImpl implements StoryService {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final IssueService is;
    private final JiraAuthenticationContext authenticationContext;
    private StoryDao storyDao;

    public StoryServiceImpl(StoryDao storyDao, IssueService is, JiraAuthenticationContext authenticationContext) {
        this.storyDao = storyDao;
        this.is = is;
        this.authenticationContext = authenticationContext;
    }

    @Override
    public StoryModel create(StoryModel storyModel, long issueId) {
        User user = authenticationContext.getLoggedInUser();
        IssueService.IssueResult issue = is.getIssue(user, issueId);
        Validate.notNull(issue, "Could not find issue for id - " + issueId);
        StoryAO story = createStory(storyModel, issue);
        StoryModel created = new StoryModel(story);
        return created;
    }

    @Override
    public StoryModel create(StoryModel storyModel, String issueKey) {
        User user = authenticationContext.getLoggedInUser();
        IssueService.IssueResult issue = is.getIssue(user, issueKey);
        Validate.notNull(issue, "Could not find issue for key - " + issueKey);
        StoryAO story = this.createStory(storyModel, issue);
        StoryModel created = new StoryModel(story);
        return created;
    }

    private StoryAO createStory(StoryModel storyModel, IssueService.IssueResult issue) {
        log.debug("$$$ Found issue is - " + issue);
        log.debug("$$$ Found issue key is - " + issue.getIssue().getKey());

        final StoryAO story = storyDao.create();
        story.setNarrative(storyModel.getNarrative());
        story.setLastRunStatus("Unknown");
        story.setIssueKey(issue.getIssue().getKey());

        story.save();

        return story;
    }

    @Override
    public StoryModel update(StoryModel storyModel) {
        StoryAO story = storyDao.read(storyModel.getId());
        story.setNarrative(storyModel.getNarrative());
        story.save();
        StoryModel udpated = new StoryModel(story);
        return udpated;
    }

    @Override
    public List<StoryModel> all() {
        List<StoryAO> all = storyDao.findAll();
        List<StoryModel> storyModels = ModelUtils.toModels(all);
        return storyModels;
    }

    @Override
    public List<StoryModel> findByIssueKey(String issueKey) {
        List<StoryAO> byIssueKey = storyDao.findByIssueKey(issueKey);
        List<StoryModel> storyModels = ModelUtils.toModels(byIssueKey);
        return storyModels;
    }

    @Override
    public StoryModel findById(Long storyId) {
        // safe downcast here
        if (storyId > new Long(Integer.MAX_VALUE)) {
            throw new RuntimeException("Story id value is greater than allowed");
        }
        StoryAO story = storyDao.read(storyId.intValue());
        StoryModel storyModel = new StoryModel(story);
        return storyModel;
    }

    @Override
    public void delete(Long storyId) {
        StoryAO story = storyDao.read(storyId.intValue());
        storyDao.delete(story);
    }
}
