package org.example;

import static org.example.MyConfig.TRACE_ID;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class MyInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    log.debug("{}={}", TRACE_ID, MDC.get(TRACE_ID));
    return HandlerInterceptor.super.preHandle(request, response, handler);
  }
}
