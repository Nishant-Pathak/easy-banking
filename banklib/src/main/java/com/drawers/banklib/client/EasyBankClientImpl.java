package com.drawers.banklib.client;

import android.content.Context;
import android.content.IntentFilter;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.drawers.banklib.client.receiver.MessageBroadcastReceiver;
import com.drawers.banklib.model.BaseModel;
import com.drawers.banklib.utils.EventListener;

import java.util.List;
import java.util.Map;

class EasyBankClientImpl extends EasyBankClient implements MessageListener {

  private final Context context;

  private final MessageBroadcastReceiver receiver;

  private final ViewGroup parent;

  private final Map<String, BaseModel> mappingModelMap;

  private final List<EventListener> listeners;
  EasyBankClientImpl(
    @NonNull Context context,
    @NonNull ViewGroup parent,
    @NonNull List<EventListener> listeners,
    @NonNull Map<String, BaseModel> mappingModelMap
  ) {
    this.context = context;
    this.parent = parent;
    this.listeners = listeners;
    this.mappingModelMap = mappingModelMap;
    this.receiver = new MessageBroadcastReceiver(this);
    // FIXME: 13/4/17 check for compatibility
    IntentFilter intentFilter = new IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
    context.registerReceiver(receiver, intentFilter);
  }

  @Override
  public void onDestroy() {
    context.unregisterReceiver(receiver);
  }

  @Override
  public void onPageFinished(WebView view, String url) {
    super.onPageFinished(view, url);
    // TODO: 13/4/17 inject appropriate javascript and show appropriate view
  }

  @Override
  public void onMessageReceived(
    @NonNull String mappingKey,
    @NonNull String sender,
    @NonNull String payload
  ) {
    // TODO: 13/4/17 Received otp, parse it and take appropriate action
  }
}
