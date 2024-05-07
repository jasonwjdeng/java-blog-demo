package org.example;

import java.util.concurrent.Callable;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

public class MyInterceptor {

  @RuntimeType
  public Object intercept(@SuperCall Callable<?> callable) throws Exception {
    System.out.println("你好！");
    Object result = callable.call();
    System.out.println("再见！");
    return result;
  }
}
