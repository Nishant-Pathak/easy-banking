package com.drawers.banklib.utils;

import android.support.annotation.NonNull;

public interface EventListener {
  void onEvent(@NonNull String eventName);
}
