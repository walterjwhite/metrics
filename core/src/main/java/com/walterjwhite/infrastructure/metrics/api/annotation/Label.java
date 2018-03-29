package com.walterjwhite.infrastructure.metrics.api.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Label {
  String value();

  /**
   * Optional help text to make this label better understood.
   *
   * @return help text
   */
  String help() default "";
}
