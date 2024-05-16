package org.example;

import static org.example.MyConfig.TRACE_ID;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@EnableAspectJAutoProxy
@SpringBootApplication
public class Main {
  @Autowired
  @Qualifier("executor")
  private Executor executor;

  @RequestMapping("/")
  public CompletableFuture<ResponseEntity<String>> home() {
    log.debug("{}={}", TRACE_ID, MDC.get(TRACE_ID));
    return CompletableFuture.completedFuture("欢迎来到码农水哥的世界！")
        .thenApplyAsync(
            s -> {
              log.debug("{}={}", TRACE_ID, MDC.get(TRACE_ID));
              return ResponseEntity.ok().body(s);
            },
            executor);
  }

  public static void main(String[] args) {
    SpringApplication.run(Main.class, args);
  }
}
