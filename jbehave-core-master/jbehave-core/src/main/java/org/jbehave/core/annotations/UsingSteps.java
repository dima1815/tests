package org.jbehave.core.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
@Documented
public @interface UsingSteps {

    Class<?>[] instances() default {};

    boolean inheritInstances() default true;

}
