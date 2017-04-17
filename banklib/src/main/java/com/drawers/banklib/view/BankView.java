package com.drawers.banklib.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

public interface BankView {
  void addToView(@NonNull Context context, @NonNull ViewGroup parent);
  void removeFromView(@NonNull ViewGroup parent);
}
