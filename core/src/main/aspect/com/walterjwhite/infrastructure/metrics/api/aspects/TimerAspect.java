package com.walterjwhite.infrastructure.metrics.api.aspects;

import com.walterjwhite.infrastructure.metrics.api.MetricsService;
import com.walterjwhite.infrastructure.metrics.api.annotation.Timer;
import com.walterjwhite.infrastructure.metrics.api.enumeration.MetricType;
import java.util.concurrent.TimeUnit;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class TimerAspect {
  private static final Logger LOGGER = LoggerFactory.getLogger(TimerAspect.class);

  @Around("execution(@Timer * *(..))")
  public Object doCounter(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    io.micrometer.core.instrument.Timer timer =
        (io.micrometer.core.instrument.Timer)
            MetricType.Timer.get(
                MetricsService.METER_REGISTRY,
                MethodSignature.class.cast(proceedingJoinPoint.getSignature()).getMethod(),
                MethodSignature.class
                    .cast(proceedingJoinPoint.getSignature())
                    .getMethod()
                    .getAnnotation(Timer.class));

    final long startTime = System.nanoTime();

    try {
      return proceedingJoinPoint.proceed();
    } finally {
      timer.record(System.nanoTime() - startTime, TimeUnit.NANOSECONDS);
    }
  }
}
