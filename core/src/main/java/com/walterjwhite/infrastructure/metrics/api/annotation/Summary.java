package com.walterjwhite.infrastructure.metrics.api.annotation;

import java.lang.annotation.*;

/** Track the size and duration of events. */
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Summary {
  String description() default "";

  String[] tags() default {};
}
