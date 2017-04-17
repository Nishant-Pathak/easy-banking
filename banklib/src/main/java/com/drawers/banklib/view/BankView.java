package com.drawers.banklib.view;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

public interface BankView {
  void addToView(@NonNull ViewGroup parent);
  void removeFromView(@NonNull ViewGroup parent);
}
