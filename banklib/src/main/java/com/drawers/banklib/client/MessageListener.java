package com.drawers.banklib.client;

import android.support.annotation.NonNull;

public interface MessageListener {
  void onMessageReceived(
    @NonNull String mappingKey,
    @NonNull String sender,
    @NonNull String payload
  );
}
