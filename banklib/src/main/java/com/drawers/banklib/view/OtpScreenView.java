package com.drawers.banklib.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.Toast;
import com.drawers.banklib.JavaInterface;
import com.drawers.banklib.R;
import com.drawers.banklib.model.ButtonModel;
import com.drawers.banklib.model.OtpModel;

import com.drawers.banklib.otpdialog.OTPDialog;
import java.lang.ref.WeakReference;

import presenter.OtpPresenter;

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
  public void addToView(@NonNull final Context context, @NonNull ViewGroup parent) {
    //LayoutInflater LAYOUT_INFLATER_SERVICE = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    /*View view = LAYOUT_INFLATER_SERVICE.inflate(R.layout.otp_screen_dialog, null);
    Button submitButton = (Button) view.findViewById(R.id.submit_button);
    submitButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        javaInterface.loadJavaScript(OtpPresenter.getOtpSubmitJavascript(model));
      }
    });
    parent.addView(view);*/

    //this.view = new WeakReference<>(view);
    // TODO: 17/4/17 test it

    // Finding otp count
    final int otpCount = model.getOtpRegex().split("\\d").length - 1;
    OTPDialog.getInstance(context)
      .withTitle(model.getLabel())                                  //.withTitle(null)  no title
      .withTitleColor("#000000")
      .withMessage("This is a modal Dialog.")                     //.withMessage(null)  no Msg
      .withMessageColor("#000000")                              //def  | withMessageColor(int resid)
      //.withIcon(getResources().getDrawable(R.drawable.icon))
      .isCancelableOnTouchOutside(true)                           //def    | isCancelable(true)
      //.withDuration(700)                                          //def
      //.withEffect(effect)                                         //def Effectstype.Slidetop
      .withButton1Text(model.getButtons().get(ButtonModel.Type.SUBMIT).getText())                                      //def gone
      .withButton2Text(model.getButtons().get(ButtonModel.Type.CANCEL).getText())
      .withButtonColor("#FFE74C3C")
      .withDialogColor("#FFFFFF")
      .isCancelable(false)
      //.withIcon(R.mipmap.ic_launcher)
      .otpCount(otpCount, android.R.color.black)
      //.setCustomView(R.layout.dialog_custom, this)
      //.setCustomView(View or ResId,context)
      .setButton1Click(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          final String otp = OTPDialog.getInstance(context).getOTP();
          Toast.makeText(v.getContext(), otp, Toast.LENGTH_SHORT).show();
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
  public void removeFromView(@NonNull ViewGroup parent) {
    // TODO: 17/4/17 test it
    if (view != null) {
      parent.removeView(view.get());
    }
  }
}
