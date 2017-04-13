package com.drawers.banklib.client;

import android.content.Context;
import android.content.IntentFilter;
import android.provider.Telephony;
import android.webkit.WebView;

import com.drawers.banklib.receiver.MessageBroadcastReceiver;
import com.drawers.banklib.utils.EventListener;

import java.util.List;

class EasyBankClientImpl extends EasyBankClient  {

  private final Context context;

  private final MessageBroadcastReceiver receiver;

  private final List<EventListener> listeners;
  EasyBankClientImpl(Context context, List<EventListener> listeners) {
    this.context = context;
    this.listeners = listeners;
    this.receiver = new MessageBroadcastReceiver(listeners);
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
  }
}
