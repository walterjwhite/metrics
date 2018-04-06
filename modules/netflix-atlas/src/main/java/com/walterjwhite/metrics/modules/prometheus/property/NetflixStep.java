package com.walterjwhite.metrics.modules.prometheus.property;

import com.walterjwhite.google.guice.property.annotation.DefaultValue;
import com.walterjwhite.google.guice.property.property.GuiceProperty;
import java.time.Duration;

public interface NetflixStep extends GuiceProperty {
  @DefaultValue Duration Default = Duration.ofSeconds(10);
}
