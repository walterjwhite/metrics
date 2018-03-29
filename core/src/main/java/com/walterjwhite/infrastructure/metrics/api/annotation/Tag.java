package com.walterjwhite.infrastructure.metrics.api.annotation;

import java.lang.annotation.*;

/**
 * TODO: redesign - Rather than assign tags at compile-time, maintain an internal map / lookup table (for a given unique tag name, these tags apply)
 */
@Deprecated
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Tag {
  String[] value();
}
