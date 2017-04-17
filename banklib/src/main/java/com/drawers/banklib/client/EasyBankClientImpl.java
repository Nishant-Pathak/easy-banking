package com.drawers.banklib.client;

import android.content.Context;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.drawers.banklib.receiver.MessageBroadcastReceiver;
import com.drawers.banklib.events.EventListener;
import com.drawers.banklib.JavaScriptInterfaces;
import com.drawers.banklib.model.BaseModel;
import com.drawers.banklib.model.OtpModel;
import com.drawers.banklib.model.PaymentChoiceModel;
import com.drawers.banklib.view.BankView;
import com.drawers.banklib.view.LoadingView;
import com.drawers.banklib.view.OtpScreenView;
import com.drawers.banklib.view.PaymentChoiceView;

import java.util.List;
import java.util.Map;

final class EasyBankClientImpl extends EasyBankClient implements MessageListener {

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
    WebView webView, @NonNull List<EventListener> listeners,
    @NonNull Map<String, BaseModel> mappingModelMap
  ) {
    this.context = context;
    this.webView = webView;
    this.parent = (ViewGroup) webView.getParent();
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
    if (loadingView != null) {
      loadingView.removeFromView(parent);
    }
  }

  @Override
  public void onPageStarted(WebView view, String url, Bitmap favicon) {
    if (bankView != null) {
      bankView.removeFromView(parent);
    }
    loadingView.addToView(context, parent);
    super.onPageStarted(view, url, favicon);
  }

  @Override
  public void onPageFinished(WebView view, String url) {
    loadingView.removeFromView(parent);
    processPageFinished(view, url);
    super.onPageFinished(view, url);
  }

  /**
   * Task to do as page loaded
   * 1. remove loading view.
   * 2. Search url regex in mappingModelMap, if found update currentModel and currentUrl
   * 3. show the new view, populated with given models.
   * @param view
   * @param url
   */
  private void processPageFinished(WebView view, String url) {
    if (bankView != null) {
      bankView.addToView(context, parent);
    }
    if (mappingModelMap.containsKey(url)) {
      currentModel = mappingModelMap.get(url);
      if (currentModel instanceof OtpModel) {
        bankView = new OtpScreenView((OtpModel) currentModel, this);
      } else if(currentModel instanceof PaymentChoiceModel) {
        bankView = new PaymentChoiceView((PaymentChoiceModel) currentModel);
      }
      receiver.setMappingKey(url);
    }

  }

  /**
   * update the currentModel with the message received.
   *
   * @param sender
   * @param payload
   */
  @Override
  public void onMessageReceived(
    @NonNull String sender,
    @NonNull String payload
  ) {
    if (currentModel instanceof OtpModel) {
      ((OtpModel)currentModel).updateMessage(sender, payload);
    }
  }

  @Override
  public void loadJavaScript(@NonNull String javaScript) {
    if (webView != null) {
      webView.loadUrl(javaScript);
    }
  }
}
