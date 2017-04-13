package com.drawers.banklib.client;

import android.webkit.WebViewClient;

public abstract class EasyBankClient extends WebViewClient {
  public abstract void onDestroy();
}
