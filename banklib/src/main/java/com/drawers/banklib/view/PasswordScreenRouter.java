package com.drawers.banklib.view;

import android.content.Context;
import android.support.annotation.NonNull;
import com.drawers.banklib.JavaInterface;
import com.drawers.banklib.model.PasswordModel;
import com.drawers.banklib.otpdialog.EnterManualView;
import com.drawers.banklib.utils.JavascriptInjectionModels;

import static com.drawers.banklib.R.style.DialogTheme;

public class PasswordScreenRouter implements BankRouter, EnterManualView.Listener {

  @NonNull private final EnterManualView enterManualDialog;
  @NonNull private final JavaInterface javaInterface;
  @NonNull private final PasswordModel passwordModel;

  public PasswordScreenRouter(
      @NonNull Context context,
      @NonNull JavaInterface javaInterface,
      @NonNull PasswordModel passwordModel
  ) {
    this.enterManualDialog = new EnterManualView(context, DialogTheme, this);
    this.javaInterface = javaInterface;
    this.passwordModel = passwordModel;
  }

  @Override public void attachToView() {
    enterManualDialog.attach();
  }

  @Override public void detachFromView() {
    enterManualDialog.detach();
  }

  @Override public void submitOtp(String otp) {
    javaInterface.loadJavaScript(
        JavascriptInjectionModels.getOtpSubmitJavascript(passwordModel, otp)
    );
  }
}
