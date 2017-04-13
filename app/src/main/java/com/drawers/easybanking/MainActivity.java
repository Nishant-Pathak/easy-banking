package com.drawers.easybanking;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;

import com.drawers.banklib.client.EasyBankClient;
import com.drawers.banklib.client.EasyBankClientBuilder;
import com.drawers.banklib.utils.EventListener;

public class MainActivity extends AppCompatActivity {

  private static final String TAG = MainActivity.class.getSimpleName();

  private WebView webView;

  private EasyBankClient easyBankClient;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    webView = (WebView) findViewById(R.id.web_view);
    webView.getSettings().setJavaScriptEnabled(true);
    easyBankClient =
      new EasyBankClientBuilder(MainActivity.this)
        .addEventListener(new EventListener() {
          @Override
          public void onEvent(@NonNull String eventName) {
            Log.d(TAG, String.format("got Event as: %s", eventName));
          }
        })
        .build();
    webView.setWebViewClient(easyBankClient);
    webView.loadUrl("https://www.freecharge.in/");
  }

  @Override
  protected void onDestroy() {
    easyBankClient.onDestroy();
    super.onDestroy();
  }
}
