package com.drawers.banklib.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import com.drawers.banklib.client.MessageListener;

public class MessageBroadcastReceiver extends BroadcastReceiver {

  private final MessageListener listener;

  public MessageBroadcastReceiver(MessageListener listener) {
    this.listener = listener;
  }

  @Override public void onReceive(Context context, Intent intent) {
    Bundle bundle = intent.getExtras();
    SmsMessage[] smsMessages = null;

    if (bundle != null) {
      Object[] pdus = (Object[]) bundle.get("pdus");
      smsMessages = new SmsMessage[pdus.length];

      for (int i = 0; i < smsMessages.length; i++) {
        smsMessages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
        String sender = smsMessages[i].getOriginatingAddress();
        String message = smsMessages[i].getMessageBody();
        listener.onMessageReceived(sender, message);
      }
    }
  }
}
