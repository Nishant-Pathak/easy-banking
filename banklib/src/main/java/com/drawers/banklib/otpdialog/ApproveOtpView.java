package com.drawers.banklib.otpdialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.drawers.banklib.R;

public class ApproveOtpView extends BaseView {

  @NonNull private final Listener listener;
  private EditText otpScreenEntryField;
  private Button otpSubmitButton;

  public ApproveOtpView(@NonNull Context context, @StyleRes int themeResId,
      @NonNull Listener listener) {
    super(context, themeResId);
    this.listener = listener;
  }

  @Override View inflateDialogView(Context context) {
    return View.inflate(context, R.layout.otp_screen_enter, null);
  }

  @Override void extractElements() {
    otpScreenEntryField = extractView(R.id.otp_screen_enter_entry);
    otpSubmitButton = extractView(R.id.otp_screen_enter_button_submit);
    otpSubmitButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        if (TextUtils.isEmpty(otpScreenEntryField.getText().toString())) {
          otpScreenEntryField.setError(getContext().getString(R.string.empty_otp));
          return;
        }
        listener.submitOtp(otpScreenEntryField.getText().toString());
      }
    });
  }

  public void setOtp(String otp) {
    otpScreenEntryField.setText(otp);
  }

  public interface Listener {
    void submitOtp(String otp);
  }
}
