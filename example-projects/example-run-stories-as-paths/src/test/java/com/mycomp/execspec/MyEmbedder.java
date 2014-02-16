package com.mycomp.execspec;

import org.jbehave.core.context.Context;
import org.jbehave.core.context.ContextView;
import org.jbehave.core.context.JFrameContextView;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.reporters.ContextOutput;
import org.jbehave.core.reporters.CrossReference;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.ContextStepMonitor;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.jbehave.core.steps.StepMonitor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dmytro on 2/15/14.
 */
public class MyEmbedder extends JUnitStories {


    public MyEmbedder() {
        Context context = new Context();
        Format contextFormat = new ContextOutput(context);
        ContextView contextView = new JFrameContextView().sized(640, 120);
        StepMonitor delegate = configuration().stepMonitor();
        ContextStepMonitor contextStepMonitor = new ContextStepMonitor(context, contextView, delegate);
        super.configuration().useStepMonitor(contextStepMonitor);
        configuration().storyReporterBuilder().withFormats(
                StoryReporterBuilder.Format.HTML,
                StoryReporterBuilder.Format.TXT,
                StoryReporterBuilder.Format.XML,
                StoryReporterBuilder.Format.CONSOLE,
                StoryReporterBuilder.Format.IDE_CONSOLE,
                StoryReporterBuilder.Format.STATS);
        CrossReference crossReference = new CrossReference();
        configuration().storyReporterBuilder().withCrossReference(crossReference);
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        return new InstanceStepsFactory(configuration(), new MyStepsMain());
    }

    @Override
    protected List<String> storyPaths() {

        List<String> storyPaths = new ArrayList<String>();
//        storyPaths.add("jira_stories/TESTING_1.story");
//        storyPaths.add("jira_stories/TESTING-2.story");
        storyPaths.add("TESTING_1.story");
        return storyPaths;
    }
}
