package com.drawers.banklib.otpdialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.View;
import android.widget.Button;
import com.drawers.banklib.R;

public class PaymentChoiceDialog extends BaseDialog {

  public PaymentChoiceDialog(@NonNull Context context, @StyleRes int themeResId) {
    super(context, themeResId);
  }

  @Override View inflateDialogView(Context context) {
    return View.inflate(context, R.layout.view_payment_choice, null);
  }

  @Override void extractElements() {
    Button passwordButton = extractView(R.id.password);
    passwordButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {

      }
    });
    Button otpButton = extractView(R.id.generate_otp);
    otpButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {

      }
    });
  }
}
