package com.drawers.banklib.view;

import android.content.Context;
import android.support.annotation.NonNull;
import com.drawers.banklib.JavaInterface;
import com.drawers.banklib.R;
import com.drawers.banklib.model.PaymentChoiceModel;
import com.drawers.banklib.otpdialog.PaymentChoiceView;

/**
 * when web url matches paymentChoiceScreen element
 */
public class PaymentChoiceRouter implements BankRouter {
  private final PaymentChoiceModel model;
  private final PaymentChoiceView paymentChoiceDialog;
  @NonNull private final JavaInterface javaInterface;

  public PaymentChoiceRouter(PaymentChoiceModel model, Context context, @NonNull
  final JavaInterface javaInterface) {
    this.model = model;
    this.paymentChoiceDialog = new PaymentChoiceView(context, R.style.DialogTheme);
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
