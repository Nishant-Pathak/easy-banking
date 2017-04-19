package com.drawers.banklib.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.drawers.banklib.JavaInterface;
import com.drawers.banklib.model.ButtonModel;
import com.drawers.banklib.model.OtpModel;
import com.drawers.banklib.otpdialog.OTPDialog;
import com.drawers.banklib.presenter.OtpPresenter;

import java.lang.ref.WeakReference;

/**
 * when web url matches otpScreen element
 */
public class OtpScreenView implements BankView {
  private final OtpModel model;
  private final JavaInterface javaInterface;
  private WeakReference<View> view;
  public OtpScreenView(OtpModel model, JavaInterface javaInterface) {
    this.model = model;
    this.javaInterface = javaInterface;
    this.view = null;
  }

  @Override
  public void attachToView(@NonNull final Context context, @NonNull ViewGroup parent) {
    final int otpCount = model.getOtpRegex().split("\\d").length - 1;
    OTPDialog.getInstance(context)
      .withTitle(model.getLabel())
      .withTitleColor("#000000")
      .withMessage("This is a modal Dialog.")
      .withMessageColor("#000000")
      .isCancelableOnTouchOutside(true)
      .withButton1Text(model.getButtons().get(ButtonModel.Type.SUBMIT).getText())
      .withButton2Text(model.getButtons().get(ButtonModel.Type.CANCEL).getText())
      .withButtonColor("#FFE74C3C")
      .withDialogColor("#FFFFFF")
      .isCancelable(false)
      .otpCount(otpCount, android.R.color.black)
      .setButton1Click(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          model.setOtp(OTPDialog.getInstance(context).getOTP());
          javaInterface.loadJavaScript(OtpPresenter.getOtpSubmitJavascript(model));
          Toast.makeText(v.getContext(), model.getOtp(), Toast.LENGTH_SHORT).show();
        }
      })
      .setButton2Click(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          OTPDialog.getInstance(context).dismiss();
        }
      })
      .show();
  }

  @Override
  public void detachFromView(@NonNull ViewGroup parent) {
    // TODO: 17/4/17 test it
    if (view != null) {
      parent.removeView(view.get());
    }
  }
}
