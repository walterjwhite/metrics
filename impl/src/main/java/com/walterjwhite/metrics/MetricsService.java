package com.walterjwhite.metrics;

import com.walterjwhite.infrastructure.inject.core.service.ShutdownAware;
import com.walterjwhite.infrastructure.inject.core.service.StartupAware;
import com.walterjwhite.metrics.api.MetricsRegistryHelper;
import io.micrometer.core.instrument.MeterRegistry;
import javax.inject.Inject;

public class MetricsService implements StartupAware, ShutdownAware {
  protected final MeterRegistry meterRegistry;

  @Inject
  public MetricsService(MeterRegistry meterRegistry) {
    this.meterRegistry = meterRegistry;
  }

  @Override
  public void onStartup() {
    MetricsRegistryHelper.setMeterRegistry(meterRegistry);
  }

  @Override
  public void onShutdown() {
    MetricsRegistryHelper.setMeterRegistry(null);

    meterRegistry.close();
  }
}
