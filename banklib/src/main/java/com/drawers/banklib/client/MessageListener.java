package com.drawers.banklib.client;

import android.support.annotation.NonNull;

public interface MessageListener {
  /**
   * update the currentModel with the message received.
   *
   * @param sender sender of the message
   * @param payload message
   */
  void onMessageReceived(
    @NonNull String sender,
    @NonNull String payload
  );
}
