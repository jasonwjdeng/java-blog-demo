package org.example;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@Order(10)
public class MyAspect {

  @Before("@annotation(org.example.MyAnnotation)")
  public void before(JoinPoint joinPoint) {
    log.debug("before");

    // Method Information
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();

    log.debug("full method description: " + signature.getMethod());
    log.debug("method name: " + signature.getMethod().getName());
    log.debug("declaring type: " + signature.getDeclaringType());

    // Method args
    log.debug("signature.getParameterNames()=" + signature.getParameterNames());
    log.debug("Method args names:");
    Arrays.stream(signature.getParameterNames()).forEach(s -> log.debug("arg name: " + s));

    log.debug("Method args types:");
    Arrays.stream(signature.getParameterTypes()).forEach(s -> log.debug("arg type: " + s));

    log.debug("Method args values:");
    Arrays.stream(joinPoint.getArgs()).forEach(o -> log.debug("arg value: " + o));

    // Additional Information
    log.debug("returning type: " + signature.getReturnType());
    log.debug("method modifier: " + Modifier.toString(signature.getModifiers()));
    Arrays.stream(signature.getExceptionTypes())
        .forEach(aClass -> log.debug("exception type: " + aClass));

    // Method annotation
    Method method = signature.getMethod();
    MyAnnotation annotation = method.getAnnotation(MyAnnotation.class);
    log.debug("Account operation annotation: " + annotation);
    log.debug("Account operation value: " + Arrays.asList(annotation.options()));
  }

  @Before("execution(* greeting(..)) && args(name)")
  public void beforeGreeting(String name) {
    log.debug("beforeGreeting, name={}", name);
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
