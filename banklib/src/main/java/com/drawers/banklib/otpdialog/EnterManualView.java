package com.drawers.banklib.otpdialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.drawers.banklib.R;

public class EnterManualView extends BaseView {

  @NonNull private final Listener listener;

  private EditText otpEntryField;

  public EnterManualView(@NonNull Context context, @StyleRes int themeResId,
      @NonNull Listener listener) {
    super(context, themeResId);
    this.listener = listener;
  }

  @Override View inflateDialogView(Context context) {
    return View.inflate(context, R.layout.otp_screen_enter_manual, null);
  }

  @Override void extractElements() {
    otpEntryField = extractView(R.id.otp_screen_enter_manual_entry);
    Button submitButton = extractView(R.id.otp_screen_enter_manual_submit);
    submitButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        if (TextUtils.isEmpty(otpEntryField.getText().toString())) {
          otpEntryField.setError(getContext().getString(R.string.empty_otp));
          return;
        }
        listener.submitOtp(otpEntryField.getText().toString());
      }
    });
  }

  public interface Listener {
    void submitOtp(String otp);
  }
}
