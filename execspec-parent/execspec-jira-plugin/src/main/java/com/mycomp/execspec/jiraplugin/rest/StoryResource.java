package com.mycomp.execspec.jiraplugin.rest;

import com.atlassian.crowd.embedded.api.User;
import com.atlassian.jira.bc.issue.search.SearchService;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.search.SearchException;
import com.atlassian.jira.issue.search.SearchResults;
import com.atlassian.jira.jql.builder.JqlQueryBuilder;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.web.bean.PagerFilter;
import com.atlassian.plugins.rest.common.security.AnonymousAllowed;
import com.atlassian.query.Query;
import com.mycomp.execspec.jiraplugin.dto.StoriesPayload;
import com.mycomp.execspec.jiraplugin.dto.StoryModel;
import com.mycomp.execspec.jiraplugin.dto.StoryPathsModel;
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
import java.util.ArrayList;
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

    private SearchService searchService;
    private JiraAuthenticationContext authenticationContext;

    public StoryResource(StoryService storyService, SearchService searchService, JiraAuthenticationContext authenticationContext) {
        this.storyService = storyService;
        this.searchService = searchService;
        this.authenticationContext = authenticationContext;
    }

    @GET
    @AnonymousAllowed
    @Path("/list-story-paths/{projectKey}")
    @Produces({MediaType.APPLICATION_JSON})
    public StoryPathsModel listStoryPaths(@PathParam("projectKey") String projectKey) {

        Validate.notEmpty(projectKey);

        List<String> paths = new ArrayList<String>();

        final JqlQueryBuilder builder = JqlQueryBuilder.newBuilder();
        builder.where().project(projectKey);
        //        .and().customField(10490L).eq("xss");
        Query query = builder.buildQuery();
        try {
            ApplicationUser appUser = authenticationContext.getUser();
            User directoryUser = appUser.getDirectoryUser();
            final SearchResults results = searchService.search(directoryUser, query, PagerFilter.getUnlimitedFilter());
            final List<Issue> issues = results.getIssues();
            for (Issue issue : issues) {
                String issueKey = issue.getKey();
                String path = projectKey + "/" + issueKey;
                paths.add(path);
            }
        } catch (SearchException e) {
            log.error("Error running search", e);
        }

        StoryPathsModel pathsModel = new StoryPathsModel();
        pathsModel.setPaths(paths);

        return pathsModel;
    }

    @GET
    @AnonymousAllowed
    @Path("/list-all")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public StoriesPayload listAll() {
        List<StoryModel> all = storyService.all();
        StoriesPayload payload = new StoriesPayload(all);
        return payload;
    }

    @GET
    @AnonymousAllowed
    @Path("/find/{projectKey}/{issueKey}")
    @Produces(MediaType.APPLICATION_JSON)
    public StoryPayload findForIssue(@PathParam("projectKey") String projectKey, @PathParam("issueKey") String issueKey) {

        StoryModel byIssueKey = storyService.findByProjectAndIssueKey(projectKey, issueKey);
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
        Validate.notNull(storyModel.getProjectKey());
        Validate.notNull(storyModel.getIssueKey());
        Validate.notEmpty(storyModel.getNarrative(), "story narrative parameter was empty");
        System.out.println("### in add method, storyModel - " + storyModel);
        storyService.create(storyModel);
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
