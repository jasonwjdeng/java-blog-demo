package org.example;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CompletableFutureExample {
  public static void main(String[] args) {

    // 创建一个 ExecutorService 来提供异步执行
    ExecutorService executor = Executors.newFixedThreadPool(2);

    // 创建一个 CompletableFuture
    CompletableFuture<Integer> future =
        CompletableFuture.supplyAsync(
            () -> {
              log.debug("[supplyAsync]计算任务开始");
              try {
                Thread.sleep(1000); // 模拟长时间运行的任务
              } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
              }
              log.debug("[supplyAsync]计算任务结束");
              return 42; // 返回计算结果
            },
            executor);

    // 应用 thenApply 方法来处理 CompletableFuture 的结果
    future
        .thenApply(
            result -> {
              log.debug("[thenApply]结果是: {}", result);
              return result * 2;
            })
        .handle(
            (result, ex) -> {
              log.debug("[handle]结果是: {}", result);
              if (null != ex) {
                log.debug("[handle]发生异常: {}", ex.getMessage());
                throw new RuntimeException("I am a new exception");
              }
              if (result > 80) {
                throw new RuntimeException("Result is larger than 80!");
              }
              return result;
            })
        .whenComplete(
            (result, ex) -> {
              log.debug("[whenComplete]结果是: {}", result);
              if (null != ex) {
                log.debug("[whenComplete]发生异常: {}", ex.getMessage());
                throw new RuntimeException("I am a new error");
              }
            })
        .exceptionally(
            ex -> {
              log.debug("[exceptionally]发生异常: {}", ex.getMessage());
              return 80;
            })
        .thenAccept(result -> log.debug("[thenAccept]结果是: {}", result))
        .thenRun(() -> log.debug("[thenRun]处理完成"));

    // 创建另一个 CompletableFuture
    CompletableFuture<String> anotherFuture =
        CompletableFuture.supplyAsync(
            () -> {
              log.debug("@[supplyAsync]另一个计算任务开始");
              try {
                Thread.sleep(1500); // 模拟长时间运行的任务
              } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
              }
              log.debug("@[supplyAsync]另一个计算任务结束");
              return "Hello, ";
            },
            executor);

    // 应用 thenCombine 方法来组合两个 CompletableFuture 的结果
    future
        .thenCombine(
            anotherFuture,
            (result, anotherResult) -> {
              log.debug("[thenCombine]result={},anotherResult={}", result, anotherResult);
              return anotherResult + "world! " + result * 2;
            })
        .thenAccept(result -> log.debug("[thenAccept]组合处理结果是: {}", result))
        .thenRun(() -> log.debug("[thenRun]组合处理完成"));

    future.join(); // 阻塞直到 Future 完成

    // 关闭 ExecutorService
    executor.shutdown();
  }
}
