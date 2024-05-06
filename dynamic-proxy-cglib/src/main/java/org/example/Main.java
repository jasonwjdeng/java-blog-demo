package org.example;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class Main {
  public static void main(String[] args) {
    // 使用CGLIB创建代理
    Enhancer enhancer = new Enhancer();
    enhancer.setSuperclass(RealClass.class);
    enhancer.setCallback(new MethodInterceptor() {
      public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("你好！");
        Object result = proxy.invokeSuper(obj, args);
        System.out.println("再见！");
        return result;
      }
    });
    RealClass proxyInstance = (RealClass) enhancer.create();

    // 使用代理对象
    proxyInstance.execute();
  }
}
