package org.example;

import jakarta.servlet.DispatcherType;
import java.util.Map;
import java.util.concurrent.Executor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class MyConfig implements WebMvcConfigurer {

  public static final String TRACE_ID = "traceId";

  @Bean
  public FilterRegistrationBean<MyFilter> myFilter() {
    FilterRegistrationBean<MyFilter> filterRegistrationBean = new FilterRegistrationBean<>();
    filterRegistrationBean.setFilter(new MyFilter());
    filterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.ASYNC);
    return filterRegistrationBean;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new MyInterceptor());
  }

  @Bean(name = "executor")
  public Executor createThreadPool() {
    ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
    taskExecutor.setCorePoolSize(4);
    taskExecutor.setThreadNamePrefix("mythread-");
    taskExecutor.setTaskDecorator(createTaskDecorator());
    return taskExecutor;
  }

  private TaskDecorator createTaskDecorator() {
    return runnable -> {
      log.debug("{}={}", TRACE_ID, MDC.get(TRACE_ID));
      Map<String, String> contextMap = MDC.getCopyOfContextMap();
      return () -> {
        log.debug("{}={}", TRACE_ID, MDC.get(TRACE_ID));
        MDC.setContextMap(contextMap);
        try {
          runnable.run();
        } finally {
          MDC.clear();
        }
      };
    };
  }
}
