package com.mycomp.execspec.jiraplugin.dto;

import com.mycomp.execspec.jiraplugin.ao.Scenario;
import com.mycomp.execspec.jiraplugin.ao.Story;
import com.mycomp.execspec.jiraplugin.dto.output.*;

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

        String issueKey = story.getIssueKey();
        String projectKey = story.getProjectKey();
        NarrativeModel narrative = parseNarrative(story.getNarrative());
        List<ScenarioModel> scenarios = parseScenarios(story.getScenarios());
        StoryModel storyModel = new StoryModel(issueKey, projectKey, narrative, scenarios);
        return storyModel;
    }

    private static NarrativeModel parseNarrative(String strNarrative) {

        String[] strLines = strNarrative.split("\\n");
        Line[] lines = new Line[strLines.length];

        String[] keywords = {"In order to", "As a", "I want to"};

        for (int i = 0; i < strLines.length; i++) {
            String strLine = strLines[i];
            LineToken[] currentLineTokens = null;
            LineKind lineKind = null;
            for (String keyword : keywords) {
                if (strLine.startsWith(keyword)) {
                    String text = strLine.substring(keyword.length());
                    currentLineTokens = new LineToken[2];
                    currentLineTokens[0] = new LineToken(keyword, LineTokenKind.keyword);
                    currentLineTokens[1] = new LineToken(text, LineTokenKind.text);
//                    lineKind = LineKind.narrative;
                    break;
                }
            }
            if (currentLineTokens == null) {
                if (strLine.startsWith("Narrative:")) {
                    String text = strLine.substring("Narrative:".length());
                    currentLineTokens = new LineToken[2];
                    currentLineTokens[0] = new LineToken("Narrative:", LineTokenKind.keyword);
                    currentLineTokens[1] = new LineToken(text, LineTokenKind.text);
//                    lineKind = LineKind.narrative;
                } else {
                    // if we got here then the line doesn't start with any of the expected keywords
                    // in this case we set no keyword
                    currentLineTokens = new LineToken[1];
                    currentLineTokens[0] = new LineToken(strLine, LineTokenKind.text);
//                    lineKind = LineKind.narrative;
                }
            }
            lines[i] = new Line(strLine, currentLineTokens, LineKind.narrative);
        }

        NarrativeModel narrative = new NarrativeModel(lines);
        return narrative;

    }

    private static List<ScenarioModel> parseScenarios(Scenario[] scenarios) {

        List<ScenarioModel> scenariosModels = new ArrayList<ScenarioModel>(scenarios.length);
        for (Scenario scenario : scenarios) {
            ScenarioModel model = parseScenario(scenario);
            scenariosModels.add(model);
        }
        return scenariosModels;
    }

    private static ScenarioModel parseScenario(Scenario scenario) {

        String[] strLines = scenario.getText().split("\\n");
        Line[] lines = new Line[strLines.length];

        for (int i = 0; i < strLines.length; i++) {

            LineKind lineKind = null;
            String strLine = strLines[i];
            LineToken[] currentLineTokens = null;

            String[] keywords = {"Given", "When", "Then"};
            for (String keyword : keywords) {
                if (strLine.startsWith(keyword)) {
                    String text = strLine.substring(keyword.length());
                    currentLineTokens = new LineToken[2];
                    currentLineTokens[0] = new LineToken(keyword, LineTokenKind.keyword);
                    currentLineTokens[1] = new LineToken(text, LineTokenKind.text);
                    lineKind = LineKind.step;
                    break;
                }
            }

            if (currentLineTokens == null) {

                String label = "Scenario:";
                if (strLine.startsWith(label)) {
                    String title = strLine.substring(label.length());
                    currentLineTokens = new LineToken[2];
                    currentLineTokens[0] = new LineToken(label, LineTokenKind.keyword);
                    currentLineTokens[1] = new LineToken(title, LineTokenKind.text);
                    lineKind = LineKind.scenario;
                } else if (strLine.startsWith("!--")) {
                    currentLineTokens = new LineToken[1];
                    currentLineTokens[0] = new LineToken(strLine, LineTokenKind.text);
                    lineKind = LineKind.comment;
                } else if (strLine.startsWith("|--")) {
                    currentLineTokens = new LineToken[1];
                    currentLineTokens[0] = new LineToken(strLine, LineTokenKind.text);
                    lineKind = LineKind.tableComment;
                } else if (strLine.startsWith("|")) {
                    currentLineTokens = new LineToken[1];
                    currentLineTokens[0] = new LineToken(strLine, LineTokenKind.text);
                    lineKind = LineKind.table;
                } else {
                    // if we got here then the line doesn't start with any of the expected keywords
                    // in this case we set no keyword
                    currentLineTokens = new LineToken[1];
                    currentLineTokens[0] = new LineToken(strLine, LineTokenKind.text);
                    lineKind = LineKind.step;
                }
            }
            lines[i] = new Line(strLine, currentLineTokens, lineKind);
        }

        ScenarioModel sm = new ScenarioModel(lines);
        return sm;
    }


    public static String asTextStory(StoryModel storyModel) {

        StringBuilder sb = new StringBuilder();
        final String LINE_BREAK = "\n";

        // narrative
        NarrativeModel narrative = storyModel.getNarrative();
        Line[] lines = narrative.getLines();
        for (Line line : lines) {
            for (LineToken lineToken : line.getTokens()) {
                sb.append(lineToken.getText());
            }
            sb.append(LINE_BREAK);
        }
        sb.append(LINE_BREAK);

        // scenarios
        List<ScenarioModel> scenarios = storyModel.getScenarios();
        for (ScenarioModel scenario : scenarios) {
            Line[] scenarioLines = scenario.getLines();
            for (Line scenarioLine : scenarioLines) {
                for (LineToken lineToken : scenarioLine.getTokens()) {
                    sb.append(lineToken.getText());
                }
                sb.append(LINE_BREAK);
            }
            sb.append(LINE_BREAK);
        }

        return sb.toString();
    }
}
