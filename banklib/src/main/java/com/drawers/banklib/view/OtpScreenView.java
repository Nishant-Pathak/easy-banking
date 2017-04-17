package com.drawers.banklib.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.drawers.banklib.R;
import com.drawers.banklib.model.OtpModel;

import java.lang.ref.WeakReference;

public class OtpScreenView implements BankView {
  private final OtpModel model;
  private WeakReference<View> otpScreenView;
  public OtpScreenView(OtpModel model) {
    this.model = model;
    this.otpScreenView = null;
  }

  @Override
  public void addToView(@NonNull Context context, @NonNull ViewGroup parent) {
    LayoutInflater LAYOUT_INFLATER_SERVICE = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View view = LAYOUT_INFLATER_SERVICE.inflate(R.layout.otp_screen_layout, parent);
    otpScreenView = new WeakReference<>(view);
    // TODO: 17/4/17 test it
  }

  @Override
  public void removeFromView(@NonNull ViewGroup parent) {
    // TODO: 17/4/17 test it
    if (otpScreenView != null) {
      parent.removeView(otpScreenView.get());
    }
  }
}
