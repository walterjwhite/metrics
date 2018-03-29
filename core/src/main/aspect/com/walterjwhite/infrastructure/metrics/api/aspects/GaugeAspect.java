package com.walterjwhite.infrastructure.metrics.api.aspects;

import com.walterjwhite.infrastructure.metrics.api.MetricsService;
import com.walterjwhite.infrastructure.metrics.api.annotation.Gauge;
import com.walterjwhite.infrastructure.metrics.api.enumeration.MetricType;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class GaugeAspect {
  private static final Logger LOGGER = LoggerFactory.getLogger(GaugeAspect.class);

  @Around("execution(@Gauge * *(..))")
  public Object doCounter(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    io.micrometer.core.instrument.Gauge gauge =
        (io.micrometer.core.instrument.Gauge)
            MetricType.Gauge.get(
                MetricsService.METER_REGISTRY,
                MethodSignature.class.cast(proceedingJoinPoint.getSignature()).getMethod(),
                MethodSignature.class
                    .cast(proceedingJoinPoint.getSignature())
                    .getMethod()
                    .getAnnotation(Gauge.class));

    try {
      // TODO: determine how to get data into the gauge
      // may need to use reflection
      return proceedingJoinPoint.proceed();
    } finally {
    }
  }
}
