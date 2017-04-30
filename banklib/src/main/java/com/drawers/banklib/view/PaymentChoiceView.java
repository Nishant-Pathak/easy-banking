package com.drawers.banklib.view;

import android.content.Context;
import android.support.annotation.NonNull;
import com.drawers.banklib.JavaInterface;
import com.drawers.banklib.R;
import com.drawers.banklib.model.PaymentChoiceModel;
import com.drawers.banklib.otpdialog.PaymentChoiceDialog;

/**
 * when web url matches paymentChoiceScreen element
 */
public class PaymentChoiceView implements BankView {
  private final PaymentChoiceModel model;
  private final PaymentChoiceDialog paymentChoiceDialog;
  @NonNull private final JavaInterface javaInterface;

  public PaymentChoiceView(PaymentChoiceModel model, Context context, @NonNull
  final JavaInterface javaInterface) {
    this.model = model;
    this.paymentChoiceDialog = new PaymentChoiceDialog(context, R.style.DialogTheme);
    this.javaInterface = javaInterface;
  }

  @Override public void attachToView() {
    paymentChoiceDialog.attach();
  }

  @Override public void detachFromView() {
    paymentChoiceDialog.detach();
  }

  //@Override
  //public void selectOtp() {
  //  javaInterface.loadJavaScript();
  //}
  //
  //@Override
  //public void selectPassword() {
  //
  //}
}
