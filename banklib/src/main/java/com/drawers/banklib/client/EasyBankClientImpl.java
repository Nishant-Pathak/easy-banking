package com.drawers.banklib.client;

import android.content.Context;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.drawers.banklib.JavaScriptInterfaces;
import com.drawers.banklib.events.EventListener;
import com.drawers.banklib.model.BaseModel;
import com.drawers.banklib.model.OtpModel;
import com.drawers.banklib.model.PaymentChoiceModel;
import com.drawers.banklib.receiver.MessageBroadcastReceiver;
import com.drawers.banklib.view.BankView;
import com.drawers.banklib.view.LoadingView;
import com.drawers.banklib.view.OtpScreenView;
import com.drawers.banklib.view.PaymentChoiceView;

import java.util.List;
import java.util.Map;
import java.util.Set;

final class EasyBankClientImpl extends EasyBankClient implements MessageListener {

  private static final String TAG = EasyBankClientImpl.class.getSimpleName();

  private final Context context;

  private final MessageBroadcastReceiver receiver;

  private final ViewGroup parent;

  private final WebView webView;

  private final Map<String, BaseModel> mappingModelMap;

  private final BankView loadingView;

  private BankView bankView;

  private BaseModel currentModel;

  EasyBankClientImpl(
    @NonNull Context context,
    WebView webView,
    @NonNull List<EventListener> listeners,
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
      loadingView.detachFromView(parent);
    }
  }

  @Override
  public void onPageStarted(WebView view, String url, Bitmap favicon) {
    if (bankView != null) {
      bankView.detachFromView(parent);
    }
    loadingView.attachToView(context, parent);
    super.onPageStarted(view, url, favicon);
  }

  @Override
  public void onPageFinished(WebView view, String url) {
    processPageFinished(view, url);
    super.onPageFinished(view, url);
  }

  /**
   * Task to do as page loaded
   * 1. remove loading view.
   * 2. Search url regex in mappingModelMap, if found update currentModel and currentUrl
   * 3. show the new view, populated with given models.
   *
   * @param view {@link WebView}
   * @param url current page url
   */
  private void processPageFinished(WebView view, String url) {
    loadingView.detachFromView(parent);
    if (isBankUrl(url)) {
      currentModel = mappingModelMap.get(url);
      if (currentModel instanceof OtpModel) {
        bankView = new OtpScreenView((OtpModel) currentModel, this);
      } else if (currentModel instanceof PaymentChoiceModel) {
        bankView = new PaymentChoiceView((PaymentChoiceModel) currentModel);
      } else {
        Log.d(TAG, String.format("%s : OtpModel not found", currentModel));
      }
      if (bankView != null) {
        bankView.attachToView(context, parent);
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onMessageReceived(
    @NonNull String sender,
    @NonNull String payload
  ) {
    if (currentModel instanceof OtpModel) {
      ((OtpModel) currentModel).updateMessage(sender, payload);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void loadJavaScript(@NonNull String javaScript) {
    if (webView != null) {
      webView.loadUrl(javaScript);
    }
  }

  /**
   * Verifies if given url matches to the configured bank url
   * @param currentUrl current page url
   * @return true if url matches, false otherwise
   */
  private boolean isBankUrl(@NonNull String currentUrl) {
    Set<String> urls = mappingModelMap.keySet();
    for(String url : urls) {
      if (currentUrl.contains(url)) {
        return true;
      }
    }
    return false;
  }
}
