package com.mycomp.execspec.jiraplugin.service;

import com.atlassian.crowd.embedded.api.User;
import com.atlassian.jira.bc.issue.IssueService;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.mycomp.execspec.jiraplugin.ao.Scenario;
import com.mycomp.execspec.jiraplugin.ao.ScenarioDao;
import com.mycomp.execspec.jiraplugin.ao.Story;
import com.mycomp.execspec.jiraplugin.ao.StoryDao;
import com.mycomp.execspec.jiraplugin.dto.ModelUtils;
import com.mycomp.execspec.jiraplugin.dto.ScenarioModel;
import com.mycomp.execspec.jiraplugin.dto.StoryModel;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public final class StoryServiceImpl implements StoryService {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final IssueService is;
    private final JiraAuthenticationContext authenticationContext;
    private StoryDao storyDao;
    private ScenarioDao scenarioDao;

    public StoryServiceImpl(StoryDao storyDao, ScenarioDao scenarioDao, IssueService is, JiraAuthenticationContext authenticationContext) {
        this.storyDao = storyDao;
        this.scenarioDao = scenarioDao;
        this.is = is;
        this.authenticationContext = authenticationContext;
    }

    @Override
    public void create(StoryModel storyModel, long issueId) {
        User user = authenticationContext.getLoggedInUser();
        IssueService.IssueResult issue = is.getIssue(user, issueId);
        Validate.notNull(issue, "Could not find issue for id - " + issueId);
        createStory(storyModel, issue);
    }

    @Override
    public void create(StoryModel storyModel, String issueKey) {
        User user = authenticationContext.getLoggedInUser();
        IssueService.IssueResult issue = is.getIssue(user, issueKey);
        Validate.notNull(issue, "Could not find issue for key - " + issueKey);
        this.createStory(storyModel, issue);
    }

    private void createStory(StoryModel storyModel, IssueService.IssueResult issue) {
        log.debug("$$$ Found issue is - " + issue);
        log.debug("$$$ Found issue key is - " + issue.getIssue().getKey());

        final Story story = storyDao.create();
        story.setNarrative(storyModel.getNarrative());
        story.setIssueKey(issue.getIssue().getKey());

        story.save();
        storyModel.setId(story.getID());

        // save the scenarios too
        List<ScenarioModel> scenarioModels = storyModel.getScenarios();
        for (ScenarioModel scenarioModel : scenarioModels) {
            Scenario scenario = scenarioDao.create();
            scenario.setText(scenarioModel.getText());
            scenario.setStory(story);
            scenario.save();
            scenarioModel.setId(scenario.getID());
        }
    }

    @Override
    public void update(StoryModel storyModel) {
        Story story = storyDao.get(storyModel.getId());
        story.setNarrative(storyModel.getNarrative());
        story.save();
    }

    @Override
    public List<StoryModel> all() {
        List<Story> all = storyDao.findAll();
        List<StoryModel> storyModels = ModelUtils.toModels(all);
        return storyModels;
    }

    @Override
    public StoryModel findByIssueKey(String issueKey) {
        List<Story> byIssueKey = storyDao.findByIssueKey(issueKey);
        if (byIssueKey.isEmpty()) {
            return null;
        } else if (byIssueKey.size() > 1) {
            throw new RuntimeException("More than one story was found for issue key - " + issueKey);
        } else {
            Story story = byIssueKey.get(0);
            StoryModel storyModel = ModelUtils.toModel(story);
            return storyModel;
        }
    }

    @Override
    public StoryModel findById(Long storyId) {
        // safe downcast here
        if (storyId > new Long(Integer.MAX_VALUE)) {
            throw new RuntimeException("Story id value is greater than allowed");
        }
        Story story = storyDao.get(storyId.intValue());
        StoryModel storyModel = ModelUtils.toModel(story);
        return storyModel;
    }

    @Override
    public void delete(Long storyId) {
        Story story = storyDao.get(storyId.intValue());
        Scenario[] scenarios = story.getScenarios();
        for (Scenario scenario : scenarios) {
            scenarioDao.delete(scenario);
        }
        storyDao.delete(story);
    }
}
