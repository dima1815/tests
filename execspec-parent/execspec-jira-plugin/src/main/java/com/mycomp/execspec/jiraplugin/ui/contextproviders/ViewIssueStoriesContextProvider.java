package com.mycomp.execspec.jiraplugin.ui.contextproviders;

import com.atlassian.crowd.embedded.api.User;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.plugin.webfragment.contextproviders.AbstractJiraContextProvider;
import com.atlassian.jira.plugin.webfragment.model.JiraHelper;
import com.mycomp.execspec.common.dto.StoryModel;
import com.mycomp.execspec.jiraplugin.service.StoryService;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewIssueStoriesContextProvider extends AbstractJiraContextProvider {

    private static final int MILLIS_IN_DAY = 24 * 60 * 60 * 1000;
    private final StoryService storyService;

    public ViewIssueStoriesContextProvider(StoryService storyService) {
        this.storyService = storyService;
    }

    @Override
    public Map getContextMap(User user, JiraHelper jiraHelper) {

        Map<String, Object> contextMap = new HashMap<String, Object>();

        Issue currentIssue = (Issue) jiraHelper.getContextParams().get("issue");
        contextMap.put("issue", currentIssue);

        Timestamp dueDate = currentIssue.getDueDate();
        if (dueDate != null) {
            int currentTimeInDays = (int) (System.currentTimeMillis() / MILLIS_IN_DAY);
            int dueDateTimeInDays = (int) (dueDate.getTime() / MILLIS_IN_DAY);
            int daysAwayFromDueDateCalc = dueDateTimeInDays - currentTimeInDays + 1;
            contextMap.put("daysAwayFromDueDate", daysAwayFromDueDateCalc);
        }


        String issueKey = currentIssue.getKey();
        List<StoryModel> storyModels = storyService.findByIssueKey(issueKey);
        contextMap.put("totalStories", storyModels.size());
        contextMap.put("stories", storyModels);

        return contextMap;
    }
}

