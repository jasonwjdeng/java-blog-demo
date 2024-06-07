package org.example;

import java.util.Map;
import java.util.WeakHashMap;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WeakHashMapDemo {
  public static void main(String[] args) {
    Object key = new Object();
    Map<Object, String> map = new WeakHashMap<>();
    map.put(key, "I am a value.");

    log.debug("map={}", map);

    key = null;
    System.gc();

    log.debug("map={}", map);
  }
}
