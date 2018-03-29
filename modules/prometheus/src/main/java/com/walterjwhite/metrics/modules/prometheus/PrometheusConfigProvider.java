package com.walterjwhite.metrics.modules.prometheus;

// import com.walterjwhite.metrics.modules.prometheus.property.PrometheusDescriptions;
// import com.walterjwhite.metrics.modules.prometheus.property.PrometheusPrefix;
// import com.walterjwhite.metrics.modules.prometheus.property.PrometheusStep;

import io.micrometer.prometheus.PrometheusConfig;
import java.time.Duration;
import javax.inject.Inject;
import javax.inject.Provider;

public class PrometheusConfigProvider implements Provider<PrometheusConfig> {
  protected final PrometheusConfig prometheusConfig;

  @Inject
  public PrometheusConfigProvider(/*@Property(PrometheusPrefix.class) String prometheusPrefix,
      @Property(PrometheusDescriptions.class) boolean prometheusDescriptions,
      @Property(PrometheusStep.class) Duration prometheusStep*/ ) {
    prometheusConfig =
        //        new DefaultPrometheusConfig(prometheusDescriptions, prometheusStep,
        // prometheusPrefix);
        new DefaultPrometheusConfig(false, Duration.ofMinutes(1), "prometheus");
  }

  @Override
  public PrometheusConfig get() {
    return prometheusConfig;
  }
}
