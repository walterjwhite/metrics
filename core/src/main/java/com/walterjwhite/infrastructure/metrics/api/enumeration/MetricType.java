package com.walterjwhite.infrastructure.metrics.api.enumeration;

import com.walterjwhite.google.guice.GuiceHelper;
import com.walterjwhite.infrastructure.metrics.api.MetricsService;
import io.micrometer.core.instrument.*;
import io.micrometer.core.instrument.search.Search;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import org.reflections.Reflections;

public enum MetricType {
  Counter(com.walterjwhite.infrastructure.metrics.api.annotation.Counter.class) {
    @Override
    public Counter get(
        MeterRegistry meterRegistry,
        Method method,
        Annotation annotation,
        final String additionalTag) {
      return search(meterRegistry, method, annotation, additionalTag).counter();
    }

    @Override
    protected Meter build(
        final MeterRegistry meterRegistry, final Method method, final Annotation annotation) {
      return io.micrometer.core.instrument.Counter.builder(getMeterName(method, annotation))
          .register(meterRegistry);
    }
  },
  Gauge(com.walterjwhite.infrastructure.metrics.api.annotation.Gauge.class) {
    @Override
    public Gauge get(
        MeterRegistry meterRegistry,
        Method method,
        Annotation annotation,
        final String additionalTag) {
      return search(meterRegistry, method, annotation, additionalTag).gauge();
    }

    @Override
    protected Meter build(
        final MeterRegistry meterRegistry, final Method method, final Annotation annotation) {
      //      return io.micrometer.core.instrument.Gauge.builder(getMeterName(method, annotation))
      //              .register(meterRegistry);
      return null;
    }
  },
  Histogram(com.walterjwhite.infrastructure.metrics.api.annotation.Histogram.class) {
    @Override
    public Meter get(
        MeterRegistry meterRegistry,
        Method method,
        Annotation annotation,
        final String additionalTag) {
      //    return meterRegistry.find(getMeterName(meteredMethod, annotation)).;
      return search(meterRegistry, method, annotation, additionalTag).summary();
    }

    @Override
    protected Meter build(
        final MeterRegistry meterRegistry, final Method method, final Annotation annotation) {
      return null;
    }
  },
  Summary(com.walterjwhite.infrastructure.metrics.api.annotation.Summary.class) {
    @Override
    public DistributionSummary get(
        MeterRegistry meterRegistry,
        Method method,
        Annotation annotation,
        final String additionalTag) {
      return search(meterRegistry, method, annotation, additionalTag).summary();
    }

    @Override
    protected Meter build(
        final MeterRegistry meterRegistry, final Method method, final Annotation annotation) {
      return io.micrometer.core.instrument.DistributionSummary.builder(
              getMeterName(method, annotation))
          .register(meterRegistry);
    }
  },
  Timer(com.walterjwhite.infrastructure.metrics.api.annotation.Timer.class) {
    @Override
    public Timer get(
        MeterRegistry meterRegistry,
        Method method,
        Annotation annotation,
        final String additionalTag) {
      return search(meterRegistry, method, annotation, additionalTag).timer();
    }

    @Override
    protected Meter build(
        final MeterRegistry meterRegistry, final Method method, final Annotation annotation) {
      return io.micrometer.core.instrument.Timer.builder(getMeterName(method, annotation))
          .register(meterRegistry);
    }
  };

  private final Class<? extends Annotation> metricAnnotationClass;

  MetricType(Class<? extends Annotation> metricAnnotationClass) {
    this.metricAnnotationClass = metricAnnotationClass;
  }

  public Meter get(
      final MeterRegistry meterRegistry, final Method method, final Annotation annotation) {
    return get(meterRegistry, method, annotation, null);
  }

  public abstract Meter get(
      final MeterRegistry meterRegistry,
      final Method method,
      final Annotation annotation,
      final String additionalTag);

  protected abstract Meter build(
      final MeterRegistry meterRegistry, final Method method, final Annotation annotation);

  public String getMeterName(Method meteredMethod, Annotation annotation) {
    return meteredMethod.getDeclaringClass().getName()
        + "."
        + meteredMethod.getName()
        + "."
        + annotation.annotationType().getSimpleName();
  }

  protected Search search(
      final MeterRegistry meterRegistry,
      final Method method,
      final Annotation annotation,
      final String additionalTag) {
    final String[] tags = getTags(method, additionalTag);
    if (tags != null && tags.length > 0)
      return meterRegistry
          .find(getMeterName(method, annotation))
          .tags(getTags(method, additionalTag));

    return meterRegistry.find(getMeterName(method, annotation));
  }

  protected String[] getTags(Method method, final String additionalTag) {
    if (method.isAnnotationPresent(
        com.walterjwhite.infrastructure.metrics.api.annotation.Tag.class)) {
      if (additionalTag != null) {
        final String[] tags =
            method
                .getAnnotation(com.walterjwhite.infrastructure.metrics.api.annotation.Tag.class)
                .value();
        final String[] newTags = Arrays.copyOf(tags, tags.length + 1);
        newTags[tags.length] = additionalTag;

        return newTags;
      }

      return method
          .getAnnotation(com.walterjwhite.infrastructure.metrics.api.annotation.Tag.class)
          .value();
    }

    if (additionalTag != null) return new String[] {additionalTag};

    return new String[] {};
  }

  public void register() {
    Reflections reflections = GuiceHelper.getGuiceInjector().getInstance(Reflections.class);

    for (Method metricEnabledMethod : reflections.getMethodsAnnotatedWith(metricAnnotationClass)) {
      Annotation annotation = metricEnabledMethod.getAnnotation(metricAnnotationClass);
      if (annotation != null) build(MetricsService.METER_REGISTRY, metricEnabledMethod, annotation);
    }
  }
}
