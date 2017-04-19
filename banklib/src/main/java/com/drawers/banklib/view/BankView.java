package com.drawers.banklib.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

public interface BankView {
  /**
   * add custom bank view to the patent
   * @param context {@link Context}
   * @param parent {@link ViewGroup}
   */
  void attachToView(@NonNull Context context, @NonNull ViewGroup parent);

  /**
   * detach view from the parent view group
   * @param parent {@link ViewGroup}
   */
  void detachFromView(@NonNull ViewGroup parent);
}
