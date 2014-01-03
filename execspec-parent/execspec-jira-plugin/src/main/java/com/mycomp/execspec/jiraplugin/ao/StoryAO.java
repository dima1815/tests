package com.mycomp.execspec.jiraplugin.ao;

import com.mycomp.execspec.common.domain.Story;
import net.java.ao.Preload;
import net.java.ao.RawEntity;
import net.java.ao.schema.AutoIncrement;
import net.java.ao.schema.NotNull;
import net.java.ao.schema.PrimaryKey;

/**
 * Entity interface has to be first in the list of extended interfaces as the getID() method has to be taken from it
 * as it contains relevant AO annotations.
 */
@Preload
public interface StoryAO extends RawEntity<Integer>, Story {

    @Override
    @AutoIncrement
    @NotNull
    @PrimaryKey("ID")
    public int getID();

}
