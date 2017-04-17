package com.drawers.banklib.view;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.drawers.banklib.model.OtpModel;

public class OtpScreenView implements BankView {
  private final OtpModel model;

  public OtpScreenView(OtpModel model) {
    this.model = model;
  }

  @Override
  public void addToView(@NonNull ViewGroup parent) {
    // TODO: 17/4/17 implement method
  }

  @Override
  public void removeFromView(@NonNull ViewGroup parent) {
    // TODO: 17/4/17 implement method
  }
}
