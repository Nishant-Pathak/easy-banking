package com.drawers.banklib.client;

import android.content.Context;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebView;
import com.drawers.banklib.JavaScriptInterfaces;
import com.drawers.banklib.events.EventListener;
import com.drawers.banklib.model.BaseModel;
import com.drawers.banklib.model.OtpModel;
import com.drawers.banklib.model.PaymentChoiceModel;
import com.drawers.banklib.receiver.MessageBroadcastReceiver;
import com.drawers.banklib.view.BankView;
import com.drawers.banklib.view.OtpScreenView;
import com.drawers.banklib.view.PaymentChoiceView;
import java.util.List;
import java.util.Map;
import java.util.Set;

final class EasyBankClientImpl extends EasyBankClient implements MessageListener {

  public static final String SMS_RECEIVED_ACTION = "android.provider.Telephony.SMS_RECEIVED";
  private static final String TAG = EasyBankClientImpl.class.getSimpleName();
  private final Context context;
  private final Map<String, BaseModel> mappingModelMap;
  private final MessageBroadcastReceiver receiver;
  private final WebView webView;
  private BankView bankView;

  private BaseModel currentModel;

  EasyBankClientImpl(@NonNull Context context, WebView webView,
      @NonNull List<EventListener> listeners, @NonNull Map<String, BaseModel> mappingModelMap) {
    this.context = context;
    this.webView = webView;
    this.mappingModelMap = mappingModelMap;
    this.receiver = new MessageBroadcastReceiver(this);
    this.bankView = null;
    IntentFilter intentFilter = new IntentFilter(SMS_RECEIVED_ACTION);
    context.registerReceiver(receiver, intentFilter);
    webView.addJavascriptInterface(new JavaScriptInterfaces(listeners), JS_INTERFACE);
  }

  @Override public void onDestroy() {
    context.unregisterReceiver(receiver);
    webView.removeJavascriptInterface(JS_INTERFACE);
    if (bankView != null) {
      bankView.detachFromView();
    }
  }

  @Override public void onPageFinished(WebView view, String url) {
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
    if (bankView != null) {
      bankView.detachFromView();
    }
    Log.d(TAG, url);
    String urlKey = isBankUrl(url);
    if (!TextUtils.isEmpty(urlKey)) {
      currentModel = mappingModelMap.get(urlKey);
      if (currentModel instanceof OtpModel) {
        bankView = new OtpScreenView(view.getContext(), (OtpModel) currentModel, this);
      } else if (currentModel instanceof PaymentChoiceModel) {
        bankView = new PaymentChoiceView((PaymentChoiceModel) currentModel, view.getContext(), this);
      } else {
        Log.d(TAG, String.format("%s : OtpModel not found", currentModel));
      }
      if (bankView != null) {
        bankView.attachToView();
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override public void onMessageReceived(@NonNull String sender, @NonNull String payload) {
    if (bankView instanceof OtpScreenView) {
      ((OtpScreenView) bankView).setOtp(sender, payload);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override public void loadJavaScript(@NonNull String javaScript) {
    if (webView != null) {
      webView.loadUrl(javaScript);
    }
  }

  /**
   * Verifies if given url matches to the configured bank url
   *
   * @param currentUrl current page url
   * @return true if url matches, false otherwise
   */
  private @Nullable String isBankUrl(@NonNull String currentUrl) {
    Set<String> urls = mappingModelMap.keySet();
    for (String url : urls) {
      if (currentUrl.contains(url)) {
        return url;
      }
    }
    return null;
  }
}
