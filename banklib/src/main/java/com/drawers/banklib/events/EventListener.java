package com.drawers.banklib.events;

import android.support.annotation.NonNull;

public interface EventListener {
  void onEvent(int code, @NonNull String eventName);
}
