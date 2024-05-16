package org.example;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
  public static void main(String[] args) throws InterruptedException {

    printThreadInfo();

    demo1();

    demo2();

    demo3();

    demo4();
  }

  private static void demo1() throws InterruptedException {
    // Create and start a virtual thread that prints a message
    Thread thread =
        Thread.ofVirtual()
            .start(
                () -> {
                  printThreadInfo();
                  System.out.println("欢迎来到码农水哥的世界！");
                });
    thread.join();
  }

  private static void demo2() throws InterruptedException {
    // Create a virtual thread named MyThread with the Thread.Builder interface
    Thread.Builder builder = Thread.ofVirtual().name("MyThread");
    Thread t =
        builder.start(
            () -> {
              printThreadInfo();
              System.out.println("欢迎来到码农水哥的世界！");
            });
    System.out.println("Thread t name: " + t.getName());
    t.join();
  }

  private static void demo3() throws InterruptedException {
    // Create and start two virtual threads with Thread.Builder
    Thread.Builder builder = Thread.ofVirtual().name("worker-", 0);

    // name "worker-0"
    Thread t1 = builder.start(Main::printThreadInfo);
    t1.join();
    System.out.println(t1.getName() + " terminated");

    // name "worker-1"
    Thread t2 = builder.start(Main::printThreadInfo);
    t2.join();
    System.out.println(t2.getName() + " terminated");
  }

  private static void demo4() {
    // Create an ExecutorService with the Executors.newVirtualThreadPerTaskExecutor() method
    try (ExecutorService myExecutor = Executors.newVirtualThreadPerTaskExecutor()) {
      Future<?> future =
          myExecutor.submit(
              () -> {
                printThreadInfo();
                System.out.println("欢迎来到码农水哥的世界！");
              });
      future.get();
      System.out.println("Task completed");
    } catch (ExecutionException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  private static void printThreadInfo() {
    System.out.printf(
        "id=%s,group=%s,name=%s%n",
        Thread.currentThread().threadId(),
        Thread.currentThread().getThreadGroup(),
        Thread.currentThread().getName());
  }
}
