package com.drawers.banklib.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.drawers.banklib.client.MessageListener;

public class MessageBroadcastReceiver extends BroadcastReceiver {

  private final MessageListener listener;

  public MessageBroadcastReceiver(MessageListener listener) {
    this.listener = listener;
  }

  @Override
  public void onReceive(Context context, Intent intent) {
    // TODO: 13/4/17 call listener with appropriate message
  }
}
