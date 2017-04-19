package com.drawers.banklib.otpdialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.InputFilter;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.drawers.banklib.R;
import com.drawers.banklib.utils.ColorUtils;
import com.drawers.banklib.utils.MultiTextWatcher;

/**
 * Class used to show OTP dialog to add otp.
 */

public class OTPDialog extends Dialog implements DialogInterface {

  private boolean isCancelable = true;
  private OnCancelListener cancelListener;

  private View mDialogView;
  private Button mButton1, mButton2;
  private ImageView mIcon;
  private TextView mTitle, mMessage;
  private static OTPDialog instance;
  private static Context tmpContext;
  private LinearLayout mLinearLayoutView;
  private FrameLayout mFrameLayoutCustomView;
  private LinearLayout mLinearLayoutTopView;
  private LinearLayout mLinearLayoutOTP;
  private MultiTextWatcher multiTextWatcher;

  public OTPDialog(@NonNull Context context) {
    super(context);
    init(context);
  }

  public OTPDialog(@NonNull Context context, @StyleRes int themeResId) {
    super(context, themeResId);
    init(context);
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    WindowManager.LayoutParams params = getWindow().getAttributes();
    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
    params.width = ViewGroup.LayoutParams.MATCH_PARENT;
    getWindow().setAttributes(params);
  }

  private void init(final Context context) {
    mDialogView = View.inflate(context, R.layout.otp_screen_dialog, null);

    mLinearLayoutView = (LinearLayout) mDialogView.findViewById(R.id.parentPanel);
    mLinearLayoutOTP = (LinearLayout) mDialogView.findViewById(R.id.llOTP);
    mFrameLayoutCustomView = (FrameLayout) mDialogView.findViewById(R.id.customPanel);
    mLinearLayoutTopView = (LinearLayout) mDialogView.findViewById(R.id.topPanel);
    mIcon = (ImageView) mDialogView.findViewById(R.id.icon);
    mTitle = (TextView) mDialogView.findViewById(R.id.alertTitle);
    mMessage = (TextView) mDialogView.findViewById(R.id.message);
    mButton1 = (Button) mDialogView.findViewById(R.id.button1);
    mButton2 = (Button) mDialogView.findViewById(R.id.button2);

    multiTextWatcher = new MultiTextWatcher();
    multiTextWatcher.setCallback(textWatcherWithInstance);
    setContentView(mDialogView);
  }

  public static OTPDialog getInstance(Context context) {

    if (instance == null || !tmpContext.equals(context)) {
      synchronized (OTPDialog.class) {
        if (instance == null || !tmpContext.equals(context)) {
          instance = new OTPDialog(context, R.style.dialog_untran);
        }
      }
    }
    tmpContext = context;
    return instance;
  }

  private MultiTextWatcher.TextWatcherWithInstance textWatcherWithInstance = new MultiTextWatcher.TextWatcherWithInstance() {

    @Override
    public void beforeTextChanged(AppCompatEditText editText, CharSequence s, int start, int count,
      int after) {

    }

    @Override
    public void onTextChanged(AppCompatEditText editText, CharSequence s, int start, int before,
      int count) {
      final int position = (int) editText.getTag();
      final int nextPosition;
      if (editText.length() == 1) {
        nextPosition = position + 1;
      } else {
        nextPosition = position - 1;
      }
      if (nextPosition >= 0 && mLinearLayoutOTP.getChildCount() > nextPosition) {
        final AppCompatEditText nextEditText =
          (AppCompatEditText) mLinearLayoutOTP.getChildAt(nextPosition);
        nextEditText.requestFocus();
      }
    }

    @Override public void afterTextChanged(AppCompatEditText editText, Editable editable) {

    }
  };

  public OTPDialog withTitle(CharSequence title) {
    toggleView(mLinearLayoutTopView, title);
    mTitle.setText(title);
    return this;
  }

  public OTPDialog withTitleColor(String colorString) {
    mTitle.setTextColor(Color.parseColor(colorString));
    return this;
  }

  public OTPDialog withTitleColor(int color) {
    mTitle.setTextColor(color);
    return this;
  }

  public OTPDialog withButtonColor(String colorString) {
    mButton1.setTextColor(Color.parseColor(colorString));
    mButton2.setTextColor(Color.parseColor(colorString));
    return this;
  }

  public OTPDialog withButtonColor(int color) {
    mButton1.setTextColor(color);
    mButton2.setTextColor(color);
    return this;
  }

  public OTPDialog withMessage(int textResId) {
    toggleView(mLinearLayoutTopView, textResId);
    mMessage.setText(textResId);
    return this;
  }

  public OTPDialog withMessage(CharSequence msg) {
    toggleView(mLinearLayoutTopView, msg);
    mMessage.setText(msg);
    return this;
  }

  public OTPDialog withMessageColor(String colorString) {
    mMessage.setTextColor(Color.parseColor(colorString));
    return this;
  }

  public OTPDialog withMessageColor(int color) {
    mMessage.setTextColor(color);
    return this;
  }

  public OTPDialog withDialogColor(String colorString) {
    if (mLinearLayoutView == null) {
      return this;
    }
    mLinearLayoutView.getBackground()
      .setColorFilter(ColorUtils.getColorFilter(Color.parseColor(colorString)));
    return this;
  }

  public OTPDialog withDialogColor(int color) {
    mLinearLayoutView.getBackground().setColorFilter(ColorUtils.getColorFilter(color));
    return this;
  }

  public OTPDialog withIcon(int drawableResId) {
    mIcon.setImageResource(drawableResId);
    return this;
  }

  public OTPDialog withIcon(Drawable icon) {
    mIcon.setImageDrawable(icon);
    return this;
  }

  private void toggleView(View view, Object obj) {
    if (obj == null) {
      view.setVisibility(View.GONE);
    } else {
      view.setVisibility(View.VISIBLE);
    }
  }

  public OTPDialog otpCount(final int otpCount, final int color) {
    if (mLinearLayoutTopView.getVisibility() == View.VISIBLE) {
      for (int i = 0; i < otpCount; i++) {
        final AppCompatEditText otpEditText = new AppCompatEditText(getContext());
        otpEditText.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        otpEditText.setTag(i);
        otpEditText.setFilters(new InputFilter[] { new InputFilter.LengthFilter(1) });
        multiTextWatcher.registerEditText(otpEditText);
        mLinearLayoutOTP.addView(otpEditText);
      }
    }
    return this;
  }

  public OTPDialog withButton1Text(CharSequence text) {
    mButton1.setVisibility(View.VISIBLE);
    mButton1.setText(text);

    return this;
  }

  public OTPDialog withButton2Text(CharSequence text) {
    mButton2.setVisibility(View.VISIBLE);
    mButton2.setText(text);
    return this;
  }

  public OTPDialog setButton1Click(View.OnClickListener click) {
    mButton1.setOnClickListener(click);
    return this;
  }

  public OTPDialog setButton2Click(View.OnClickListener click) {
    mButton2.setOnClickListener(click);
    return this;
  }

  public OTPDialog setCustomView(int resId, Context context) {
    View customView = View.inflate(context, resId, null);
    if (mFrameLayoutCustomView.getChildCount() > 0) {
      mFrameLayoutCustomView.removeAllViews();
    }
    mFrameLayoutCustomView.addView(customView);
    mFrameLayoutCustomView.setVisibility(View.VISIBLE);
    mLinearLayoutTopView.setVisibility(View.GONE);
    return this;
  }

  public OTPDialog setCustomView(View view, Context context) {
    if (mFrameLayoutCustomView.getChildCount() > 0) {
      mFrameLayoutCustomView.removeAllViews();
    }
    mFrameLayoutCustomView.addView(view);
    mFrameLayoutCustomView.setVisibility(View.VISIBLE);
    mLinearLayoutTopView.setVisibility(View.GONE);
    return this;
  }

  public OTPDialog isCancelableOnTouchOutside(boolean cancelable) {
    this.isCancelable = cancelable;
    this.setCanceledOnTouchOutside(cancelable);
    return this;
  }

  public OTPDialog isCancelable(boolean cancelable) {
    this.isCancelable = cancelable;
    this.setCancelable(cancelable);
    return this;
  }

  @Override public void show() {
    super.show();
  }

  @Override public void dismiss() {
    super.dismiss();
    mButton1.setVisibility(View.GONE);
    mButton2.setVisibility(View.GONE);
  }

  public String getOTP() {
    String otp = "";
    if (mLinearLayoutTopView.getVisibility() == View.VISIBLE
      && mLinearLayoutOTP.getChildCount() > 0) {
      for (int i = 0; i < mLinearLayoutOTP.getChildCount(); i++) {
        final AppCompatEditText editText = (AppCompatEditText) mLinearLayoutOTP.getChildAt(i);
        otp += editText.getText().toString();
      }
    }
    return otp;
  }
}
