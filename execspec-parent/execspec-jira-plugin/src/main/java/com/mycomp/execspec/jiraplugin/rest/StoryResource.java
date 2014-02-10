package com.mycomp.execspec.jiraplugin.rest;

import com.atlassian.plugins.rest.common.security.AnonymousAllowed;
import com.mycomp.execspec.jiraplugin.dto.StoriesPayload;
import com.mycomp.execspec.jiraplugin.dto.StoryModel;
import com.mycomp.execspec.jiraplugin.dto.StoryPayload;
import com.mycomp.execspec.jiraplugin.service.StoryService;
import org.apache.commons.lang.Validate;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

/**
 * Contains rest api methods related to processing of Story objects.
 *
 * @author stasyukd
 * @since 2.0.0-SNAPSHOT
 */
@Path("/story")
public class StoryResource {

    private Logger log = LoggerFactory.getLogger(this.getClass());

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
    @Path("/find-for-issue/{issueKey}")
    @Produces(MediaType.APPLICATION_JSON)
    public StoryPayload findForIssue(@PathParam("issueKey") String issueKey) {

        StoryModel byIssueKey = storyService.findByIssueKey(issueKey);
        StoryPayload payload = new StoryPayload(byIssueKey);
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
//    public StoryModel add(String storyModelString) {
    public StoryModel add(String storyModelString) {
        ObjectMapper mapper = new ObjectMapper();
        StoryModel storyModel = null;
        try {
            storyModel = mapper.readValue(storyModelString, StoryModel.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.debug("adding storyModel: \n" + storyModel);
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
