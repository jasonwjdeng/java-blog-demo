package org.example;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

public class Main {
  public static void main(String[] args) throws InstantiationException, IllegalAccessException {
    Class<? extends RealClass> dynamicType =
        new ByteBuddy()
            .subclass(RealClass.class)
            .method(ElementMatchers.named("execute"))
            .intercept(MethodDelegation.to(new MyInterceptor()))
            .make()
            .load(Main.class.getClassLoader())
            .getLoaded();
    dynamicType.newInstance().execute();
  }
}
