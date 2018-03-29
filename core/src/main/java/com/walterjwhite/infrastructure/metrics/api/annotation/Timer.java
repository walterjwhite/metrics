package com.walterjwhite.infrastructure.metrics.api.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Timer {
  String description() default "";

  String[] tags() default {};
}
