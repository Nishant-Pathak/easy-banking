package com.drawers.easybanking;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.drawers.banklib.client.EasyBankClient;
import com.drawers.banklib.client.EasyBankClientBuilder;

public class MainActivity extends AppCompatActivity {

  private WebView webView;
  private EasyBankClient easyBankClient;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    webView = (WebView) findViewById(R.id.web_view);
    webView.getSettings().setJavaScriptEnabled(true);
    easyBankClient = new EasyBankClientBuilder(MainActivity.this).build();
    webView.setWebViewClient(easyBankClient);

  }

  @Override
  protected void onDestroy() {
    easyBankClient.onDestroy();
    super.onDestroy();
  }
}
