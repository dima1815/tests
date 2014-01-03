package com.mycomp.execspec.jiraplugin.rest;

import com.atlassian.plugins.rest.common.security.AnonymousAllowed;
import com.mycomp.execspec.common.dto.StoriesPayload;
import com.mycomp.execspec.common.dto.StoryModel;
import com.mycomp.execspec.jiraplugin.service.StoryService;
import org.apache.commons.lang.Validate;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Contains rest api methods related to processing of Story objects.
 *
 * @author stasyukd
 * @since 2.0.0-SNAPSHOT
 */
@Path("/story")
public class StoryResource {


    private final StoryService storyService;

    public StoryResource(StoryService storyService) {
        this.storyService = storyService;
    }

    @GET
    @AnonymousAllowed
    @Path("/list-all")
    @Produces(MediaType.APPLICATION_JSON)
    public StoriesPayload listAll() {
        List<StoryModel> all = storyService.all();
        StoriesPayload payload = new StoriesPayload(all);
        return payload;
    }

    @GET
    @AnonymousAllowed
    @Path("/list-for-issue/{issueKey}")
    @Produces(MediaType.APPLICATION_JSON)
    public StoriesPayload listForIssue(@PathParam("issueKey") String issueKey) {

        List<StoryModel> byIssueKey = storyService.findByIssueKey(issueKey);
        StoriesPayload payload = new StoriesPayload(byIssueKey);
        return payload;
    }

    @POST
    @AnonymousAllowed
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public StoryModel update(StoryModel storyModel) {
        Validate.notNull(storyModel);
        Validate.notNull(storyModel.getIssueKey());
        Validate.isTrue(storyModel.getId() != 0);
        System.out.println("### in update method, storyModel - " + storyModel);
        storyService.update(storyModel);
        return storyModel;
    }

    @POST
    @AnonymousAllowed
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public StoryModel add(StoryModel storyModel) {
        Validate.notNull(storyModel);
        Validate.notNull(storyModel.getIssueKey());
        Validate.notEmpty(storyModel.getNarrative(), "story narrative parameter was empty");
        System.out.println("### in add method, storyModel - " + storyModel);
        storyService.create(storyModel, storyModel.getIssueKey());
        return storyModel;
    }

    @DELETE
    @AnonymousAllowed
    @Path("/delete/{storyId}")
    public Response delete(@PathParam("storyId") Long storyId) {
        storyService.delete(storyId);
        return Response.ok("Successful deletion from server!").build();
    }


}
