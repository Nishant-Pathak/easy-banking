package com.drawers.easybanking;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.drawers.banklib.client.EasyBankClient;
import com.drawers.banklib.client.EasyBankClientBuilder;
import com.drawers.banklib.events.EventListener;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

  private static final String TAG = MainActivity.class.getSimpleName();

  private WebView webView;

  private FrameLayout parentView;

  private EasyBankClient easyBankClient;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    webView = (WebView) findViewById(R.id.web_view);
    parentView = (FrameLayout) findViewById(R.id.fl_activity_main);
    webView.getSettings().setJavaScriptEnabled(true);
    try {
      easyBankClient =
        new EasyBankClientBuilder(MainActivity.this, parentView, webView)
          .addEventListener(new EventListener() {
            @Override
            public void onEvent(int code, @NonNull String eventName) {
              Log.d(TAG, String.format("got Event %d as: %s", code, eventName));
            }
          })
          .build();
    } catch (IOException e) {
      e.printStackTrace();
      finish();
      return;
    }
    webView.setWebViewClient(easyBankClient);
    webView.loadUrl("https://www.freecharge.in/");
  }

  @Override
  protected void onDestroy() {
    easyBankClient.onDestroy();
    super.onDestroy();
  }
}
