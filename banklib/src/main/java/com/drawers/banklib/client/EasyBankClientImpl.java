package com.drawers.banklib.client;

import android.content.Context;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebView;
import com.drawers.banklib.JavaScriptInterfaces;
import com.drawers.banklib.model.BaseModel;
import com.drawers.banklib.model.OtpModel;
import com.drawers.banklib.model.PasswordModel;
import com.drawers.banklib.model.PaymentChoiceModel;
import com.drawers.banklib.receiver.MessageBroadcastReceiver;
import com.drawers.banklib.utils.BankLibConstants;
import com.drawers.banklib.view.BankRouter;
import com.drawers.banklib.view.OtpScreenRouter;
import com.drawers.banklib.view.PasswordScreenRouter;
import com.drawers.banklib.view.PaymentChoiceRouter;
import java.util.Map;
import java.util.Set;

final class EasyBankClientImpl extends EasyBankClient implements MessageListener {

  private static final String TAG = EasyBankClientImpl.class.getSimpleName();
  private final Context context;
  private final Map<String, BaseModel> mappingModelMap;
  private final MessageBroadcastReceiver receiver;
  private final WebView webView;
  private BankRouter bankRouter;

  EasyBankClientImpl(@NonNull Context context,
      @NonNull WebView webView,
      @NonNull JavaScriptInterfaces javaScriptInterfaces,
      @NonNull Map<String, BaseModel> mappingModelMap,
      @NonNull MessageBroadcastReceiver messageBroadcastReceiver
  ) {
    this.context = context;
    this.webView = webView;
    this.mappingModelMap = mappingModelMap;
    this.receiver = messageBroadcastReceiver;
    this.receiver.attach(this);
    webView.addJavascriptInterface(javaScriptInterfaces, JS_INTERFACE);
    context.registerReceiver(receiver, new IntentFilter(BankLibConstants.SMS_RECEIVED_ACTION));
  }

  @Override public void onDestroy() {
    receiver.detach();
    context.unregisterReceiver(receiver);
    webView.removeJavascriptInterface(JS_INTERFACE);
    if (bankRouter != null) {
      bankRouter.detachFromView();
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
    if (bankRouter != null) {
      bankRouter.detachFromView();
    }
    String urlKey = isBankUrl(url);
    if (!TextUtils.isEmpty(urlKey)) {
      BaseModel currentModel = mappingModelMap.get(urlKey);
      if (currentModel instanceof OtpModel) {
        bankRouter = new OtpScreenRouter(view.getContext(), (OtpModel) currentModel, this);
      } else if (currentModel instanceof PaymentChoiceModel) {
        bankRouter = new PaymentChoiceRouter((PaymentChoiceModel) currentModel, view.getContext(), this);
      } else if (currentModel instanceof PasswordModel) {
        bankRouter = new PasswordScreenRouter(view.getContext(), this, (PasswordModel) currentModel);
      } else {
        Log.d(TAG, String.format("%s : OtpModel not found", currentModel));
      }
      if (bankRouter != null) {
        bankRouter.attachToView();
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override public void onMessageReceived(@NonNull String sender, @NonNull String payload) {
    if (bankRouter instanceof OtpScreenRouter) {
      ((OtpScreenRouter) bankRouter).setOtp(sender, payload);
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
