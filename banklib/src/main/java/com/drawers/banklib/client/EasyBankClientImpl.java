package com.drawers.banklib.client;

import android.content.Context;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.drawers.banklib.client.receiver.MessageBroadcastReceiver;
import com.drawers.banklib.events.EventListener;
import com.drawers.banklib.events.JavaScriptInterfaces;
import com.drawers.banklib.model.BaseModel;
import com.drawers.banklib.model.OtpModel;
import com.drawers.banklib.model.PaymentChoiceModel;
import com.drawers.banklib.view.BankView;
import com.drawers.banklib.view.LoadingView;
import com.drawers.banklib.view.OtpScreenView;
import com.drawers.banklib.view.PaymentChoiceView;

import java.util.List;
import java.util.Map;

class EasyBankClientImpl extends EasyBankClient implements MessageListener {

  private final Context context;

  private final MessageBroadcastReceiver receiver;

  private final ViewGroup parent;

  private final WebView webView;

  private final Map<String, BaseModel> mappingModelMap;

  private final BankView loadingView;

  private BankView bankView;

  private BaseModel currentModel;

  private String currentUrl;

  EasyBankClientImpl(
    @NonNull Context context,
    @NonNull ViewGroup parent,
    WebView webView, @NonNull List<EventListener> listeners,
    @NonNull Map<String, BaseModel> mappingModelMap
  ) {
    this.context = context;
    this.parent = parent;
    this.webView = webView;
    this.mappingModelMap = mappingModelMap;
    this.receiver = new MessageBroadcastReceiver(this);
    this.bankView = null;
    this.loadingView = new LoadingView();
    // FIXME: 13/4/17 check for compatibility
    IntentFilter intentFilter = new IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
    context.registerReceiver(receiver, intentFilter);
    webView.addJavascriptInterface(new JavaScriptInterfaces(listeners), JS_INTERFACE);

  }

  @Override
  public void onDestroy() {
    context.unregisterReceiver(receiver);
    webView.removeJavascriptInterface(JS_INTERFACE);
  }

  @Override
  public void onPageStarted(WebView view, String url, Bitmap favicon) {
    currentUrl = url;
    if (bankView != null) {
      bankView.removeFromView(parent);
    }
    loadingView.addToView(parent);
    // TODO: 17/4/17 iterate on all keys and check for regex match
    if (mappingModelMap.containsKey(url)) {
      currentModel = mappingModelMap.get(url);
      if (currentModel instanceof OtpModel) {
        bankView = new OtpScreenView((OtpModel) currentModel);
      } else if(currentModel instanceof PaymentChoiceModel) {
        bankView = new PaymentChoiceView((PaymentChoiceModel) currentModel);
      }
      receiver.setMappingKey(url);
    }
    super.onPageStarted(view, url, favicon);
  }

  @Override
  public void onPageFinished(WebView view, String url) {
    loadingView.removeFromView(parent);
    if (bankView != null) {
      bankView.addToView(parent);
    }
    // TODO: 13/4/17 inject appropriate javascript and show appropriate view
    super.onPageFinished(view, url);
  }

  @Override
  public void onMessageReceived(
    @NonNull String url,
    @NonNull String sender,
    @NonNull String payload
  ) {
    if (url.equals(currentUrl)) {
      if (currentModel instanceof OtpModel) {
        ((OtpModel)currentModel).updateMessage(sender, payload);
      }
    }
  }
}
