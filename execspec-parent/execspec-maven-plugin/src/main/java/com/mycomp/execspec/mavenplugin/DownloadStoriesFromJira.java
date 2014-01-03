package com.mycomp.execspec.mavenplugin;

/**
 * TODO - add at least one line of java doc comment.
 *
 * @author stasyukd
 * @since 2.0.0-SNAPSHOT
 */


import com.mycomp.execspec.common.dto.StoriesPayload;
import com.mycomp.execspec.common.dto.StoryModel;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Says "Hi" to the user.
 */
@Mojo(name = "downloadstories")
public class DownloadStoriesFromJira extends AbstractMojo {

    /**
     * The greeting to display.
     */
    @Parameter(property = "downloadstories.listStoriesUrl", defaultValue = "http://prgdwm395319:2990/jira/rest/storyservice/1.0/story/list-all")
    private String listStoriesUrl;

    public void execute() throws MojoExecutionException {

        getLog().info("listStoriesUrl = " + listStoriesUrl);

        Client client = Client.create();
        WebResource res = client.resource(listStoriesUrl);
        res.type(MediaType.APPLICATION_JSON);
        ClientResponse response = res.get(ClientResponse.class);
        getLog().info("response - " + response);
        if (response.getStatus() == 200) {
            StoriesPayload storiesPayload = response.getEntity(StoriesPayload.class);
            List<StoryModel> stories = storiesPayload.getStories();
            getLog().info("stories.size() = " + stories.size());
            getLog().info("stories - " + stories);
        } else {
            getLog().info("Response was ERROR");
        }


    }

}
