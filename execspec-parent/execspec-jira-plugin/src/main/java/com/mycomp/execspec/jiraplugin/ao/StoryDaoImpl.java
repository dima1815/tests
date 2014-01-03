package com.mycomp.execspec.jiraplugin.ao;

import com.atlassian.activeobjects.external.ActiveObjects;
import net.java.ao.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;

public final class StoryDaoImpl implements StoryDao {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final ActiveObjects ao;

    public StoryDaoImpl(ActiveObjects ao) {
        this.ao = checkNotNull(ao);
    }

    @Override
    public StoryAO create() {
        final StoryAO story = ao.create(StoryAO.class);
        return story;
    }

    @Override
    public StoryAO read(int storyId) {
        StoryAO story = ao.get(StoryAO.class, storyId);
        return story;
    }

    @Override
    public void delete(StoryAO story) {
        ao.delete(story);
    }

    @Override
    public List<StoryAO> findAll() {
        return newArrayList(ao.find(StoryAO.class));
    }

    @Override
    public List<StoryAO> findByIssueKey(String issueKey) {
        String params = issueKey;
        Query query = Query.select().where("ISSUE_KEY = ?", params);
        StoryAO[] result = ao.find(StoryAO.class, query);
        return newArrayList(result);
    }
}
