package com.walterjwhite.infrastructure.metrics.api.annotation;

import java.lang.annotation.*;

/** Track the size and number of events in buckets. */
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Histogram {
  String description() default "";

  String[] tags() default {};
}
