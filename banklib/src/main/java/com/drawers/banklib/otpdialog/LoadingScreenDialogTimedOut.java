package com.drawers.banklib.otpdialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.View;
import android.widget.Button;

import com.drawers.banklib.R;

public class LoadingScreenDialogTimedOut extends BaseDialog {

    @NonNull private final Listener mListener;

    public LoadingScreenDialogTimedOut(@NonNull Context context, @StyleRes int themeResId, @NonNull Listener listener) {
        super(context, themeResId);
        mListener = listener;
    }

    @Override
    View inflateDialogView(Context context) {
        return View.inflate(context, R.layout.otp_screen_wait_timeout, null);
    }

    @Override
    void extractElements() {
        Button resendOtpButton = extractView(R.id.otp_screen_wait_timeout_resend_otp);
        resendOtpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.resendOtpForLoadingScreen();
            }
        });
        Button enterManualButton = extractView(R.id.otp_screen_wait_timeout_enter_manual);
        enterManualButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.enterManually();
            }
        });
    }

    public interface Listener {
        void resendOtpForLoadingScreen();
        void enterManually();
    }
}
