package com.mycomp.execspec.jiraplugin.dto;

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

            StoryModel storyModel = new StoryModel(story);
            storyModels.add(storyModel);
        }

        return storyModels;
    }

}
