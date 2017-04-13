package com.drawers.banklib.client.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.drawers.banklib.client.MessageListener;

public class MessageBroadcastReceiver extends BroadcastReceiver {

  private final MessageListener listener;

  private String mappingKey;

  public MessageBroadcastReceiver(MessageListener listener) {
    this.listener = listener;
  }

  public void setMappingKey(String mappingKey) {
    this.mappingKey = mappingKey;
  }

  @Override
  public void onReceive(Context context, Intent intent) {
    // TODO: 13/4/17 call listener with appropriate message
  }
}
