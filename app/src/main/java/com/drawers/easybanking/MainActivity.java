package com.drawers.easybanking;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import com.drawers.banklib.client.EasyBankClient;
import com.drawers.banklib.client.EasyBankBuilder;
import com.drawers.banklib.events.EventCode;
import com.drawers.banklib.events.EventListener;

public class MainActivity extends AppCompatActivity {

  private static final String TAG = MainActivity.class.getSimpleName();
  private EasyBankClient easyBankClient;
  private WebView webView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    webView = (WebView) findViewById(R.id.web_view);
    webView.getSettings().setJavaScriptEnabled(true);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      WebView.setWebContentsDebuggingEnabled(true);
    }
    easyBankClient = new EasyBankBuilder()
        .addEventListener(new EventListener() {
          @Override public void onEvent(@NonNull EventCode code, @NonNull String eventName) {
            Log.d(TAG, String.format("got Event %s as: %s", code, eventName));
          }
        }).build(MainActivity.this, webView);
    webView.setWebViewClient(easyBankClient);
    webView.loadUrl("https://www.freecharge.in/");
  }

  @Override protected void onDestroy() {
    easyBankClient.onDestroy();
    super.onDestroy();
  }
}
