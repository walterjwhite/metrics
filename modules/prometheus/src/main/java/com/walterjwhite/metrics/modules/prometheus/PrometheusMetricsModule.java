package com.walterjwhite.metrics.modules.prometheus;

import com.walterjwhite.infrastructure.metrics.api.AbstractMetricsModule;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.prometheus.PrometheusConfig;

public class PrometheusMetricsModule extends AbstractMetricsModule {

  @Override
  protected void configure() {
    super.configure();

    bind(PrometheusHttpServer.class).asEagerSingleton();
    bind(PrometheusConfig.class)
        .toProvider(PrometheusConfigProvider.class) /*.in(Singleton.class)*/;
    //    bind(PrometheusMeterRegistry.class)
    //        .toProvider(PrometheusMeterRegistryProvider.class)
    //        .in(Singleton.class);
    bind(MeterRegistry.class)
        .toProvider(PrometheusMeterRegistryProvider.class) /*.in(Singleton.class)*/;
  }
}
