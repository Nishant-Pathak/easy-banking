package com.drawers.banklib.otpdialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.View;
import android.widget.Button;
import com.drawers.banklib.R;

public class LoadingScreenDialogTimedOut extends BaseDialog {

  @NonNull private final Listener listener;

  public LoadingScreenDialogTimedOut(@NonNull Context context, @StyleRes int themeResId,
      @NonNull Listener listener) {
    super(context, themeResId);
    this.listener = listener;
  }

  @Override View inflateDialogView(Context context) {
    return View.inflate(context, R.layout.otp_screen_wait_timeout, null);
  }

  @Override void extractElements() {
    Button resendOtpButton = extractView(R.id.otp_screen_wait_timeout_resend_otp);
    resendOtpButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        listener.resendOtpForLoadingScreen();
      }
    });
    Button enterManualButton = extractView(R.id.otp_screen_wait_timeout_enter_manual);
    enterManualButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        listener.enterManually();
      }
    });
  }

  public interface Listener {
    void resendOtpForLoadingScreen();

    void enterManually();
  }
}
