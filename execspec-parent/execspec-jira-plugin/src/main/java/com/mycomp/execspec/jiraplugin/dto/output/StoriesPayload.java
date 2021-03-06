package com.mycomp.execspec.jiraplugin.dto.output;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Container for lists of StoryModel objects.
 *
 * @author stasyukd
 */
@XmlRootElement(name = "stories_payload")
@XmlAccessorType(XmlAccessType.FIELD)
public class StoriesPayload {

    @XmlElement
    private List<StoryModel> stories;

    /**
     * Constructor for use via reflection.
     */
    protected StoriesPayload() {
    }

    public StoriesPayload(List<StoryModel> stories) {
        this.stories = stories;
    }

    public List<StoryModel> getStories() {
        return stories;
    }

}
