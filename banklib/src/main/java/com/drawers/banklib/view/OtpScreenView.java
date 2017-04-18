package com.drawers.banklib.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.drawers.banklib.JavaInterface;
import com.drawers.banklib.R;
import com.drawers.banklib.model.OtpModel;

import java.lang.ref.WeakReference;

import com.drawers.banklib.presenter.OtpPresenter;

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
  public void addToView(@NonNull Context context, @NonNull ViewGroup parent) {
    LayoutInflater LAYOUT_INFLATER_SERVICE = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View view = LAYOUT_INFLATER_SERVICE.inflate(R.layout.otp_screen_layout, null);
    Button submitButton = (Button) view.findViewById(R.id.submit_button);
    submitButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        javaInterface.loadJavaScript(OtpPresenter.getOtpSubmitJavascript(model));
      }
    });
    parent.addView(view);
    this.view = new WeakReference<>(view);
    // TODO: 17/4/17 test it
  }

  @Override
  public void removeFromView(@NonNull ViewGroup parent) {
    // TODO: 17/4/17 test it
    if (view != null) {
      parent.removeView(view.get());
    }
  }
}
