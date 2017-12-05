package com.drawers.banklib.otpdialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.drawers.banklib.R;

public class LoadingScreenView extends BaseView {

  @NonNull private Listener listener;
  private TextView timerView;

  public LoadingScreenView(@NonNull Context context, @StyleRes int themeResId,
      @NonNull Listener listener) {
    super(context, themeResId);
    this.listener = listener;
  }

  @Override View inflateDialogView(Context context) {
    return View.inflate(context, R.layout.otp_screen_wait, null);
  }

  @Override void extractElements() {
    timerView = extractView(R.id.otp_screen_wait_timer);
    Button enterManualButton = extractView(R.id.otp_screen_wait_enter_manual);
    enterManualButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        listener.enterManually();
      }
    });
  }

  @Override public void attach() {
    super.attach();
    listener.startTimer();
  }

  @Override public void detach() {
    listener.stopTimer();
    super.detach();
  }

  public void tick(long l) {
    timerView.setText(String.valueOf((int) (l / 1000)));
  }

  public interface Listener {
    void enterManually();
    void startTimer();
    void stopTimer();
  }
}
