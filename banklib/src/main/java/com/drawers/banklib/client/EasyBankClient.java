package com.drawers.banklib.client;

import android.webkit.WebViewClient;
import com.drawers.banklib.JavaInterface;

public abstract class EasyBankClient extends WebViewClient implements JavaInterface {
  public static final String JS_INTERFACE = "EasyBank";

  public abstract void onDestroy();
}
