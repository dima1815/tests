package com.mycomp.execspec.jiraplugin.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Container for lists of StoryModel objects.
 *
 * @author stasyukd
 */
@XmlRootElement(name = "stories_payload")
@XmlAccessorType(XmlAccessType.FIELD)
public class StoryPayload {

    @XmlElement
    private StoryModel story;

    /**
     * Constructor for use via reflection.
     */
    protected StoryPayload() {
    }

    public StoryPayload(StoryModel story) {
        this.story = story;
    }

    public StoryModel getStory() {
        return story;
    }

}
