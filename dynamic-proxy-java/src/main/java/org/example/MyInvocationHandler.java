package org.example;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

// 调用处理器
public class MyInvocationHandler implements InvocationHandler {

  private final Object target;

  public MyInvocationHandler(Object target) {
    this.target = target;
  }

  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    System.out.println("你好！");
    Object result = method.invoke(target, args);
    System.out.println("再见！");
    return result;
  }
}
