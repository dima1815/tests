package org.jbehave.core.annotations;

import org.jbehave.core.io.StoryFinder;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
@Documented
public @interface UsingPaths {

    String searchIn();

    String[] includes() default {"**/*.story"};

    String[] excludes() default {};

    Class<? extends StoryFinder> storyFinder() default StoryFinder.class;

}
