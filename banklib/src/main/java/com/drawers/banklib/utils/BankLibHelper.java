package com.drawers.banklib.utils;

import com.drawers.banklib.client.EasyBankClient;

public class BankLibHelper {

  public static void requireNonNull(Object... objects) {
    for(Object o: objects) {
      requireNonNull(o);
    }
  }

  private static <T> T requireNonNull(T obj) {
    if (obj == null)
      throw new NullPointerException();
    return obj;
  }

  public static String JAVASCRIPT_FUNCTION_TEMPLATE =
    "javascript: (function(){\n" +
      "        try {\n" +
      "           %s" +
      "           window." + EasyBankClient.JS_INTERFACE + ".logEvent(0, '%s');\n" +
      "        }\n" +
      "        catch(err) {\n" +
      "           window." + EasyBankClient.JS_INTERFACE +".logEvent(1, 'err');\n" +
      "        }\n" +
      "      })();";
}
