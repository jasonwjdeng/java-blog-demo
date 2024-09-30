package org.example;

import com.google.common.base.Strings;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
  @MyAnnotation(options = {"home"})
  public String home() {
    return "Welcome to my world";
  }

  @RequestMapping("/me")
  @MyAnnotation(options = {"me"})
  public ResponseEntity<String> me() {
    return ResponseEntity.ok().body("I am Shuige");
  }

  @RequestMapping("/greeting")
  @MyAnnotation(options = {"greeting"})
  public CompletableFuture<ResponseEntity<String>> greeting(
      @RequestParam(value = "name", required = false) String name) {
    return CompletableFuture.supplyAsync(
            () -> {
              log.debug("sleep 3 seconds");
              try {
                TimeUnit.SECONDS.sleep(3);
              } catch (InterruptedException e) {
                throw new MyException(e);
              }
              String greeting = "欢迎来到码农水哥的世界！";
              if (!Strings.isNullOrEmpty(name)) {
                greeting = name + "," + greeting;
              }
              return greeting;
            },
            executor)
        .thenApplyAsync(
            s -> {
              log.debug("handling request");
              return ResponseEntity.ok().body(s);
            },
            executor);
  }

  @RequestMapping("/boom")
  @MyAnnotation(options = {"boom"})
  public String boom() {
    throw new MyException("BOOM!");
  }

  @RequestMapping("/bang")
  @MyAnnotation(options = {"bang"})
  public CompletableFuture<String> bang() {
    return CompletableFuture.failedFuture(new MyException("BANG!"));
  }

  public static void main(String[] args) {
    SpringApplication.run(Main.class, args);
  }
}
