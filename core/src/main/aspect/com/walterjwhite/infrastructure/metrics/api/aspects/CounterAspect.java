package com.walterjwhite.infrastructure.metrics.api.aspects;

import com.walterjwhite.infrastructure.metrics.api.MetricsService;
import com.walterjwhite.infrastructure.metrics.api.annotation.Counter;
import com.walterjwhite.infrastructure.metrics.api.enumeration.MetricType;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class CounterAspect {
  private static final Logger LOGGER = LoggerFactory.getLogger(CounterAspect.class);

  // TODO: test the use of tags, especially try/catch/finally should be an enum ...
  @Around("execution(@Counter * *(..))")
  public Object doCounter(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    try {
      getCounter(proceedingJoinPoint, "try").increment();
      return proceedingJoinPoint.proceed();
    } catch (Exception e) {
      getCounter(proceedingJoinPoint, "catch").increment();
      throw (e);
    } finally {
      getCounter(proceedingJoinPoint, "finally").increment();
    }
  }

  protected io.micrometer.core.instrument.Counter getCounter(
      final ProceedingJoinPoint proceedingJoinPoint, final String tag) {
    return (io.micrometer.core.instrument.Counter)
        MetricType.Counter.get(
            MetricsService.METER_REGISTRY,
            MethodSignature.class.cast(proceedingJoinPoint.getSignature()).getMethod(),
            MethodSignature.class
                .cast(proceedingJoinPoint.getSignature())
                .getMethod()
                .getAnnotation(Counter.class),
            tag);
  }
}
