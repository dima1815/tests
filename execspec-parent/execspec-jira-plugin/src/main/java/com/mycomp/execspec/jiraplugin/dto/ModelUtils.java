package com.mycomp.execspec.jiraplugin.dto;

import com.mycomp.execspec.jiraplugin.ao.Scenario;
import com.mycomp.execspec.jiraplugin.ao.Story;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains utility methods useful while working with model objects.
 *
 * @author stasyukd
 * @since 2.0.0-SNAPSHOT
 */
public class ModelUtils {

    public static List<StoryModel> toModels(List<? extends Story> stories) {

        List<StoryModel> storyModels = new ArrayList<StoryModel>(stories.size());
        for (Story story : stories) {

            StoryModel storyModel = toModel(story);
            storyModels.add(storyModel);
        }

        return storyModels;
    }

    public static StoryModel toModel(Story story) {

        StoryModel storyModel = new StoryModel();
        storyModel.setId(story.getID());
        storyModel.setIssueKey(story.getIssueKey());
        storyModel.setProjectKey(story.getProjectKey());
        storyModel.setNarrative(story.getNarrative());

        Scenario[] scenarios = story.getScenarios();
        List<ScenarioModel> scenarioModels = new ArrayList<ScenarioModel>(scenarios.length);
        for (Scenario scenario : scenarios) {
            ScenarioModel scenarioModel = new ScenarioModel();
            scenarioModel.setId(scenario.getID());
            scenarioModel.setText(scenario.getText());
            scenarioModels.add(scenarioModel);
        }
        storyModel.setScenarios(scenarioModels);

        return storyModel;
    }

    public static String asTextStory(StoryModel storyModel) {

        StringBuilder sb = new StringBuilder();
        final String LINE_BREAK = "\n";

        // narrative
        sb.append("Narrative:");
        sb.append(LINE_BREAK);
        sb.append(storyModel.getNarrative());
        sb.append(LINE_BREAK);

        List<ScenarioModel> scenarios = storyModel.getScenarios();
        for (ScenarioModel scenario : scenarios) {
            sb.append(LINE_BREAK);
            sb.append(scenario.getText());
        }

        return sb.toString();
    }
}
