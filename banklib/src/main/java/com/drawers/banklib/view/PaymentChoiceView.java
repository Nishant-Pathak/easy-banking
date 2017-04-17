package com.drawers.banklib.view;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.drawers.banklib.model.PaymentChoiceModel;

public class PaymentChoiceView implements BankView {
  private final PaymentChoiceModel model;

  public PaymentChoiceView(PaymentChoiceModel model) {
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
