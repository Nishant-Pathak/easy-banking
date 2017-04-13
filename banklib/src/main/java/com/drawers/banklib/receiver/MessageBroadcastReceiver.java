package com.drawers.banklib.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.drawers.banklib.utils.EventListener;

import java.util.List;

public class MessageBroadcastReceiver extends BroadcastReceiver {
  public MessageBroadcastReceiver(@NonNull List<EventListener> listeners) {

  }

  @Override
  public void onReceive(Context context, Intent intent) {

  }
}
