package org.jbehave.core.annotations;

import org.jbehave.core.embedder.Embedder;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
@Documented
public @interface UsingEmbedder {

    Class<?> embedder() default Embedder.class;

    boolean batch() default false;

    boolean skip() default false;

    boolean generateViewAfterStories() default true;

    boolean ignoreFailureInStories() default false;

    boolean ignoreFailureInView() default false;

    boolean verboseFailures() default false;

    boolean verboseFiltering() default false;

    long storyTimeoutInSecs() default 300;

    int threads() default 1;

    boolean stepsFactory() default true;

    String[] metaFilters() default {};

    String systemProperties() default "";

}
