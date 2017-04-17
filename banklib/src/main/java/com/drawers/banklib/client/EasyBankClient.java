package com.drawers.banklib.client;

import android.webkit.WebViewClient;

public abstract class EasyBankClient extends WebViewClient {
  public static final String JS_INTERFACE = "EasyBank";

  public abstract void onDestroy();
}
