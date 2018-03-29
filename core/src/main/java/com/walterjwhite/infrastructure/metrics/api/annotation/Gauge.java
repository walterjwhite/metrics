package com.walterjwhite.infrastructure.metrics.api.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Gauge {
  String description() default "";

  String[] tags() default {};
}
