package com.drawers.banklib.utils;

import com.drawers.banklib.client.EasyBankClient;
import com.drawers.banklib.events.EventCode;

public class BankLibHelper {

  public static String JAVASCRIPT_FUNCTION_TEMPLATE = "javascript: (function(){\n"
      + "        try {\n"
      + "           %s"
      + "           window."
      + EasyBankClient.JS_INTERFACE
      + ".logEvent(" + EventCode.SUCCESS + ", '%s');\n"
      + "        }\n"
      + "        catch(err) {\n"
      + "           window."
      + EasyBankClient.JS_INTERFACE
      + ".logEvent(" + EventCode.FAILURE + ", 'err');\n"
      + "        }\n"
      + "      })();";

  public static void requireNonNull(Object... objects) {
    for (Object o : objects) {
      requireNonNull(o);
    }
  }

  private static <T> T requireNonNull(T obj) {
    if (obj == null) throw new NullPointerException();
    return obj;
  }

  private BankLibHelper() {
    throw new AssertionError("Initialization not possible");
  }
}
