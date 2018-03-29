package com.walterjwhite.infrastructure.metrics.api;

import com.walterjwhite.infrastructure.metrics.api.enumeration.MetricType;
import io.micrometer.core.instrument.MeterRegistry;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// NOTE: if this is part of a web service / web application, this may fail because the CLI injector
// is different from the web injector
// the web injector should succeed
@Singleton
public class MetricsService {
  private static final Logger LOGGER = LoggerFactory.getLogger(MetricsService.class);

  public static MeterRegistry METER_REGISTRY;

  @Inject
  public MetricsService(MeterRegistry meterRegistry) {
    super();
    METER_REGISTRY = meterRegistry;

    LOGGER.info("starting metrics");
    registerMeters();
  }

  protected void registerMeters() {
    for (MetricType metricType : MetricType.values()) metricType.register();
  }
}
