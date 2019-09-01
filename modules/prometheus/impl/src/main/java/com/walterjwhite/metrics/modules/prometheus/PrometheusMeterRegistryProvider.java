package com.walterjwhite.metrics.modules.prometheus;

import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
public class PrometheusMeterRegistryProvider implements Provider<PrometheusMeterRegistry> {
  protected final PrometheusMeterRegistry prometheusMeterRegistry;

  @Inject
  public PrometheusMeterRegistryProvider(PrometheusConfig prometheusConfig) {
    prometheusMeterRegistry = new PrometheusMeterRegistry(prometheusConfig);
  }

  @Produces
  @Singleton
  @Override
  public PrometheusMeterRegistry get() {
    return prometheusMeterRegistry;
  }
}
