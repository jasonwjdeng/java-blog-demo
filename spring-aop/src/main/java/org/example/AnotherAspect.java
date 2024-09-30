package org.example;

import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@Order(20)
public class AnotherAspect {

  @Before("@annotation(org.example.MyAnnotation)")
  public void before() {
    log.debug("before");
  }

  @AfterReturning(pointcut = "@annotation(org.example.MyAnnotation)", returning = "retVal")
  public void afterReturning(Object retVal) {
    log.debug("afterReturning, retVal={}", retVal);
  }

  @AfterThrowing(pointcut = "@annotation(org.example.MyAnnotation)", throwing = "ex")
  public void afterThrowing(Throwable ex) {
    log.debug("afterThrowing, ex={}", ex.getMessage());
  }

  @After("@annotation(org.example.MyAnnotation)")
  public void after() {
    log.debug("after");
  }

  @Around("@annotation(annotation)")
  public Object around(ProceedingJoinPoint pjp, MyAnnotation annotation) throws Throwable {
    log.debug("around, before proceed");
    log.debug("options={}", Arrays.asList(annotation.options()));
    Object result = pjp.proceed();
    log.debug("around, after proceed");
    return result;
  }
}
