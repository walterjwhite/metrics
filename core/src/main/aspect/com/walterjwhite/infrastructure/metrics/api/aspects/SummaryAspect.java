package com.walterjwhite.infrastructure.metrics.api.aspects;

import com.walterjwhite.infrastructure.metrics.api.MetricsService;
import com.walterjwhite.infrastructure.metrics.api.annotation.Summary;
import com.walterjwhite.infrastructure.metrics.api.enumeration.MetricType;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class SummaryAspect {
  private static final Logger LOGGER = LoggerFactory.getLogger(SummaryAspect.class);

  // TODO: implement
  @Around("execution(@Summary * *(..))")
  public Object doCounter(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    io.micrometer.core.instrument.DistributionSummary summary =
        (io.micrometer.core.instrument.DistributionSummary)
            MetricType.Summary.get(
                MetricsService.METER_REGISTRY,
                MethodSignature.class.cast(proceedingJoinPoint.getSignature()).getMethod(),
                MethodSignature.class
                    .cast(proceedingJoinPoint.getSignature())
                    .getMethod()
                    .getAnnotation(Summary.class));

    try {

      return proceedingJoinPoint.proceed();
    } finally {
      // summary.record();
    }
  }
}
