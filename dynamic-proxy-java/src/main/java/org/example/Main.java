package org.example;

import java.lang.reflect.Proxy;

public class Main {
  public static void main(String[] args) {

    // 创建代理
    Service serviceProxy = (Service) Proxy.newProxyInstance(
            RealService.class.getClassLoader(),
            new Class<?>[]{Service.class},
            new MyInvocationHandler(new RealService())
    );

    // 使用代理对象
    serviceProxy.execute();
  }
}
