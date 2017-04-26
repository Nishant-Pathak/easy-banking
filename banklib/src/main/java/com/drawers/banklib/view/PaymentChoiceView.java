package com.drawers.banklib.view;

import com.drawers.banklib.model.PaymentChoiceModel;
import com.drawers.banklib.otpdialog.BaseDialog;

/**
 * when web url matches paymentChoiceScreen element
 */
public class PaymentChoiceView extends BankView {
  private final PaymentChoiceModel model;

  public PaymentChoiceView(PaymentChoiceModel model) {
    this.model = model;
  }

  @Override
  BaseDialog getCurrentDialog() {
    return null;
  }
}
