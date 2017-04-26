package com.drawers.banklib.client;

import android.content.Context;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.util.Log;
import android.webkit.WebView;

import com.drawers.banklib.JavaScriptInterfaces;
import com.drawers.banklib.events.EventListener;
import com.drawers.banklib.model.BaseModel;
import com.drawers.banklib.model.ButtonModel;
import com.drawers.banklib.model.OtpModel;
import com.drawers.banklib.model.PaymentChoiceModel;
import com.drawers.banklib.receiver.MessageBroadcastReceiver;
import com.drawers.banklib.view.BankView;
import com.drawers.banklib.view.OtpScreenView;
import com.drawers.banklib.view.PaymentChoiceView;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

final class EasyBankClientImpl extends EasyBankClient implements MessageListener, OtpScreenView.Listener {

  private static final String TAG = EasyBankClientImpl.class.getSimpleName();

  public static final String SMS_RECEIVED_ACTION = "android.provider.Telephony.SMS_RECEIVED";

  private final Context context;

  private final MessageBroadcastReceiver receiver;

  private final WebView webView;

  private final Map<String, BaseModel> mappingModelMap;

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
    this.mappingModelMap = mappingModelMap;
    this.receiver = new MessageBroadcastReceiver(this);
    this.bankView = null;
    IntentFilter intentFilter = new IntentFilter(SMS_RECEIVED_ACTION);
    context.registerReceiver(receiver, intentFilter);
    webView.addJavascriptInterface(new JavaScriptInterfaces(listeners), JS_INTERFACE);
  }

  @Override
  public void onDestroy() {
    context.unregisterReceiver(receiver);
    webView.removeJavascriptInterface(JS_INTERFACE);
    if (bankView != null) {
      bankView.detachFromView();
    }
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
    if (bankView != null) {
      bankView.detachFromView();
    }
//    EnumMap<ButtonModel.Type, ButtonModel> buttons = new EnumMap<>(ButtonModel.Type.class);
//    buttons.put(ButtonModel.Type.SUBMIT, new ButtonModel(ButtonModel.Type.SUBMIT, "Approve", "Approve"));
//    buttons.put(ButtonModel.Type.CANCEL, new ButtonModel(ButtonModel.Type.CANCEL, "Cancel", "Cancel"));
//    currentModel = new OtpModel("Sdsd", "OTP Received", "SDds", "Sdds", 30, buttons);
//    bankView = new OtpScreenView((OtpModel) currentModel, this, view.getContext(), this);
//    bankView.attachToView();
    if (isBankUrl(url)) {
      currentModel = mappingModelMap.get(url);
      if (currentModel instanceof OtpModel) {
        bankView = new OtpScreenView((OtpModel) currentModel, this, view.getContext(), this);
      } else if (currentModel instanceof PaymentChoiceModel) {
        bankView = new PaymentChoiceView((PaymentChoiceModel) currentModel);
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
  @Override
  public void onMessageReceived(
    @NonNull String sender,
    @NonNull String payload
  ) {
    if (bankView instanceof OtpScreenView) {
      ((OtpScreenView)bankView).setOtp(sender, payload);
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

  @Override
  public void submitOtp(String javascript) {
    webView.loadUrl(javascript);
  }
}
