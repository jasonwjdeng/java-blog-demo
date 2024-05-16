package org.example;

import static org.example.MyConfig.TRACE_ID;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class MyAspect {

  @Around("execution(* org.example.Main.home())")
  public Object checkMdc(ProceedingJoinPoint pjp) throws Throwable {
    log.debug("before proceed, {}={}", TRACE_ID, MDC.get(TRACE_ID));
    Object result = pjp.proceed();
    log.debug("after proceed, {}={}", TRACE_ID, MDC.get(TRACE_ID));
    return result;
  }
}
