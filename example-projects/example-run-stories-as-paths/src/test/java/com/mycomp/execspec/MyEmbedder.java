package com.mycomp.execspec;

import org.jbehave.core.context.Context;
import org.jbehave.core.context.ContextView;
import org.jbehave.core.context.JFrameContextView;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.reporters.*;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;

import java.util.List;

/**
 * Created by Dmytro on 2/15/14.
 */
public class MyEmbedder extends JUnitStories {

    private final String jiraBaseUrl = "http://ideapad:2990/jira";

    public MyEmbedder() {
        Context context = new Context();
        Format contextFormat = new ContextOutput(context);
        ContextView contextView = new JFrameContextView().sized(640, 120);
//        StepMonitor delegate = configuration().stepMonitor();
//        ContextStepMonitor contextStepMonitor = new ContextStepMonitor(context, contextView, delegate);
//        super.configuration().useStepMonitor(contextStepMonitor);
        configuration().storyReporterBuilder().withFormats(
                StoryReporterBuilder.Format.HTML,
                StoryReporterBuilder.Format.TXT,
                StoryReporterBuilder.Format.XML,
                StoryReporterBuilder.Format.CONSOLE,
                StoryReporterBuilder.Format.IDE_CONSOLE,
                StoryReporterBuilder.Format.STATS);
        CrossReference crossReference = new CrossReference();
        configuration().storyReporterBuilder().withCrossReference(crossReference);

        JiraStoryLoader jiraLoader = new JiraStoryLoader();
        jiraLoader.setJiraBaseUrl(jiraBaseUrl);
        configuration().useStoryLoader(jiraLoader);

        StoryReporter jiraStoryReporter = new JiraStoryReporter();
        configuration().useDefaultStoryReporter(jiraStoryReporter);
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        return new InstanceStepsFactory(configuration(), new MyStepsMain());
    }

    @Override
    protected List<String> storyPaths() {

        JiraStoryFinder storyFinder = new JiraStoryFinder();

        List<String> includes = null;
        List<String> excludes = null;
        List<String> paths = null;
        String projectKey = "TESTING";
        paths = storyFinder.findPaths(jiraBaseUrl, projectKey, includes, excludes);

        System.out.println("Found paths are:\n" + paths);

        return paths;
    }
}
