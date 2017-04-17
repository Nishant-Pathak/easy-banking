package com.drawers.banklib.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.drawers.banklib.model.PaymentChoiceModel;

/**
 * when web url matches paymentChoiceScreen element
 */
public class PaymentChoiceView implements BankView {
  private final PaymentChoiceModel model;

  public PaymentChoiceView(PaymentChoiceModel model) {
    this.model = model;
  }

  @Override
  public void addToView(@NonNull Context context, @NonNull ViewGroup parent) {
    // TODO: 17/4/17 implement method
  }

  @Override
  public void removeFromView(@NonNull ViewGroup parent) {
    // TODO: 17/4/17 implement method
  }
}
