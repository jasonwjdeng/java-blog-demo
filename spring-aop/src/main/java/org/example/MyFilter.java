package org.example;

import static org.example.MyConfig.TRACE_ID;

import jakarta.servlet.*;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

@Slf4j
public class MyFilter implements Filter {

  @Override
  public void doFilter(
      ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
      throws IOException, ServletException {

    log.debug("{}={}", TRACE_ID, MDC.get(TRACE_ID));
    log.debug("clear MDC");
    MDC.clear();

    String traceId;
    if (Objects.isNull(servletRequest.getAttribute(TRACE_ID))) {
      traceId = UUID.randomUUID().toString();
      servletRequest.setAttribute(TRACE_ID, traceId);
    } else {
      traceId = servletRequest.getAttribute(TRACE_ID).toString();
    }

    MDC.put(TRACE_ID, traceId);

    filterChain.doFilter(servletRequest, servletResponse);
  }
}
