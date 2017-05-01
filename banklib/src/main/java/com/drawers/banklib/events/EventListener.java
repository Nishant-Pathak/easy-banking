package com.drawers.banklib.events;

import android.support.annotation.NonNull;

public interface EventListener {
  void onEvent(@NonNull EventCode code, @NonNull String eventName);
}
