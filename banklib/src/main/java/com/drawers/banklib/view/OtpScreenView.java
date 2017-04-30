package com.drawers.banklib.view;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.util.Log;
import com.drawers.banklib.JavaInterface;
import com.drawers.banklib.R;
import com.drawers.banklib.model.OtpModel;
import com.drawers.banklib.otpdialog.ApproveOtpDialog;
import com.drawers.banklib.otpdialog.BaseDialog;
import com.drawers.banklib.otpdialog.EnterManualDialog;
import com.drawers.banklib.otpdialog.LoadingScreenDialog;
import com.drawers.banklib.otpdialog.LoadingScreenDialogTimedOut;
import com.drawers.banklib.presenter.OtpPresenter;
import java.util.regex.Matcher;

/**
 * when web url matches otpScreen element
 */
public class OtpScreenView extends BankView
    implements LoadingScreenDialog.Listener, LoadingScreenDialogTimedOut.Listener,
    ApproveOtpDialog.Listener, EnterManualDialog.Listener {

  @NonNull private final ApproveOtpDialog mApproveOtpDialog;
  @NonNull private final EnterManualDialog mEnterManualDialog;
  @NonNull private final JavaInterface mJavaInterface;
  @NonNull private final LoadingScreenDialog mLoadingScreenDialog;
  @NonNull private final LoadingScreenDialogTimedOut mLoadingScreenDialogTimedOut;
  @NonNull private final OtpModel mModel;
  @NonNull private OtpScreenState currentScreenState;
  @NonNull private BaseDialog mBaseDialog;
  @NonNull private CountDownTimer mCountDownTimer;

  public OtpScreenView(@NonNull final OtpModel model, @NonNull final JavaInterface javaInterface,
      @NonNull final Context context) {
    mModel = model;
    mJavaInterface = javaInterface;
    mApproveOtpDialog = new ApproveOtpDialog(context, R.style.DialogTheme, this);
    mLoadingScreenDialog = new LoadingScreenDialog(context, R.style.DialogTheme, this);
    mLoadingScreenDialogTimedOut =
        new LoadingScreenDialogTimedOut(context, R.style.DialogTheme, this);
    mEnterManualDialog = new EnterManualDialog(context, R.style.DialogTheme, this);
    mBaseDialog = mLoadingScreenDialog;
    currentScreenState = OtpScreenState.LOADING;
    mCountDownTimer = new CountDownTimer(30000, 1000) {
      @Override public void onTick(long l) {
        mLoadingScreenDialog.tick(l);
      }

      @Override public void onFinish() {
        moveToTimedOutState();
      }
    };
  }

  private void moveToTimedOutState() {
    switch (currentScreenState) {
      case LOADING:
        moveToState(OtpScreenState.LOADING_TIMEOUT);
        break;
      default:
        throw new RuntimeException(
            String.format("Incorrect state for timeout %1s", currentScreenState));
    }
  }

  @Override @NonNull BaseDialog getCurrentDialog() {
    return mBaseDialog;
  }

  private void moveToState(OtpScreenState otpScreenState) {
    mBaseDialog.dismiss();
    moveToNextState(otpScreenState);
    mBaseDialog.show();
  }

  @Override public void attachToView() {
    super.attachToView();
    mCountDownTimer.start();
  }

  @Override public void detachFromView() {
    mCountDownTimer.cancel();
    super.detachFromView();
  }

  private void moveToNextState(OtpScreenState nextState) {
    switch (currentScreenState) {
      case LOADING:
        mCountDownTimer.cancel();
        switch (nextState) {
          case ENTER_MANUAL:
            mBaseDialog = mEnterManualDialog;
            currentScreenState = OtpScreenState.ENTER_MANUAL;
            break;
          case LOADING_TIMEOUT:
            mBaseDialog = mLoadingScreenDialogTimedOut;
            currentScreenState = OtpScreenState.LOADING_TIMEOUT;
            break;
          case APPROVE:
            mBaseDialog = mApproveOtpDialog;
            currentScreenState = OtpScreenState.APPROVE;
            break;
          default:
            throwIncorrectStateTransition(nextState);
        }
        break;
      case LOADING_TIMEOUT:
        switch (nextState) {
          case ENTER_MANUAL:
            mBaseDialog = mEnterManualDialog;
            currentScreenState = OtpScreenState.ENTER_MANUAL;
            break;
          case LOADING:
            mBaseDialog = mLoadingScreenDialog;
            currentScreenState = OtpScreenState.LOADING;
            mCountDownTimer.start();
            break;
          default:
            throwIncorrectStateTransition(nextState);
        }
        break;
      default:
        throwIncorrectStateTransition(nextState);
    }
  }

  private void throwIncorrectStateTransition(OtpScreenState nextScreenState) {
    throw new RuntimeException(
        String.format("Incorrect state transition from %1s to %2s", currentScreenState,
            nextScreenState));
  }

  @Override public void enterManually() {
    moveToState(OtpScreenState.ENTER_MANUAL);
  }

  @Override public void resendOtpForLoadingScreen() {
    mJavaInterface.loadJavaScript(OtpPresenter.getResendOtpJavascript(mModel));
    moveToState(OtpScreenState.LOADING);
  }

  public void setOtp(String sender, String payload) {
    if (sender == null /*|| !sender.equals(mModel.getOtpSender())*/ || payload == null) return;
    Matcher matcher = mModel.getPattern().matcher(payload);
    if (matcher.find()) {
      String otp = matcher.group(0);
      Log.d("TAG", otp);
      mApproveOtpDialog.setOtp(otp);
      moveToState(OtpScreenState.APPROVE);
    }
  }

  @Override public void submitOtp(String otp) {
    mJavaInterface.loadJavaScript(OtpPresenter.getOtpSubmitJavascript(mModel, otp));
  }

  public enum OtpScreenState {
    LOADING, LOADING_TIMEOUT, ENTER_MANUAL, APPROVE
  }
}
