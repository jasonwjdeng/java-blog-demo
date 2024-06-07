package org.example;

import java.lang.ref.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
  public static void main(String[] args) throws InterruptedException {
    Object a = new Object();
    Object b = new Object();
    Object c = new Object();

    SoftReference<Object> aRef = new SoftReference<>(a);
    WeakReference<Object> bRef = new WeakReference<>(b);
    ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
    PhantomReference<Object> cRef = new PhantomReference<>(c, referenceQueue);

    log.debug("@@@Show init value");
    log.debug("a={}, aRef.get()={}, aRef={}", a, aRef.get(), aRef);
    log.debug("b={}, bRef.get()={}, bRef={}", b, bRef.get(), bRef);
    log.debug("c={}, cRef.get()={}, cRef={}", c, cRef.get(), cRef);
    log.debug(cRef.refersTo(c) ? "cRef refers to c" : "cRef is clear");

    a = null;
    b = null;
    c = null;

    log.debug("@@@Before GC");
    log.debug("a={}, aRef.get()={}, aRef={}", a, aRef.get(), aRef);
    log.debug("b={}, bRef.get()={}, bRef={}", b, bRef.get(), bRef);
    log.debug("c={}, cRef.get()={}, cRef={}", c, cRef.get(), cRef);
    log.debug(cRef.refersTo(null) ? "cRef is clear" : "cRef refers to c");

    log.debug("@@@Call GC");
    System.gc();

    Thread.ofVirtual()
        .start(
            () -> {
              while (true) {
                Reference<?> reference = referenceQueue.poll();
                if (null == reference) {
                  log.debug("@@@reference is null");
                  break;
                }
                log.debug("@@@reference={}", reference);
              }
            })
        .join();

    log.debug("@@@After GC");
    log.debug("a={}, aRef.get()={}, aRef={}", a, aRef.get(), aRef);
    log.debug("b={}, bRef.get()={}, bRef={}", b, bRef.get(), bRef);
    log.debug("c={}, cRef.get()={}, cRef={}", c, cRef.get(), cRef);
    log.debug(cRef.refersTo(null) ? "cRef is clear" : "cRef refers to c");
  }
}
