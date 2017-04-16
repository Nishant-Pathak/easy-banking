package com.drawers.banklib.utils;

public class BankLibHelper {
  public static void requireNonNull(Object... objects) {
    for(Object o: objects) {
      requireNonNull(o);
    }
  }

  public static <T> T requireNonNull(T obj) {
    if (obj == null)
      throw new NullPointerException();
    return obj;
  }
}
