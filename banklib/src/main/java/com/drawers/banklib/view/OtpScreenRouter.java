package com.drawers.banklib.view;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import com.drawers.banklib.JavaInterface;
import com.drawers.banklib.model.OtpModel;
import com.drawers.banklib.otpdialog.ApproveOtpDialog;
import com.drawers.banklib.otpdialog.BaseDialog;
import com.drawers.banklib.otpdialog.EnterManualDialog;
import com.drawers.banklib.otpdialog.LoadingScreenDialog;
import com.drawers.banklib.otpdialog.LoadingScreenDialogTimedOut;
import com.drawers.banklib.otpdialog.SubmitDialog;
import com.drawers.banklib.utils.JavascriptInjectionModels;
import java.util.regex.Matcher;

import static android.util.Log.d;
import static com.drawers.banklib.R.style.DialogTheme;
import static com.drawers.banklib.otpdialog.LoadingScreenDialog.Listener;
import static com.drawers.banklib.utils.JavascriptInjectionModels.getOtpSubmitJavascript;
import static com.drawers.banklib.view.OtpScreenRouter.OtpScreenState.APPROVE;
import static com.drawers.banklib.view.OtpScreenRouter.OtpScreenState.ENTER_MANUAL;
import static com.drawers.banklib.view.OtpScreenRouter.OtpScreenState.LOADING;
import static com.drawers.banklib.view.OtpScreenRouter.OtpScreenState.LOADING_TIMEOUT;
import static com.drawers.banklib.view.OtpScreenRouter.OtpScreenState.SUBMIT;
import static java.lang.String.format;

/**
 * when web url matches otpScreen element
 */
public class OtpScreenRouter implements BankRouter,
    Listener,
    LoadingScreenDialogTimedOut.Listener,
    ApproveOtpDialog.Listener,
    EnterManualDialog.Listener {

  @NonNull private final ApproveOtpDialog approveOtpDialog;
  @NonNull private BaseDialog baseDialog;
  @NonNull private OtpScreenState currentScreenState;
  @NonNull private final EnterManualDialog enterManualDialog;
  @NonNull private final JavaInterface javaInterface;
  @NonNull private final LoadingScreenDialog loadingScreenDialog;
  @NonNull private final LoadingScreenDialogTimedOut loadingScreenDialogTimedOut;
  @NonNull private final SubmitDialog submitDialog;
  @NonNull private CountDownTimer countDownTimer;
  @NonNull private final OtpModel otpModel;

  public OtpScreenRouter(
      @NonNull Context context,
      @NonNull final OtpModel otpModel,
      @NonNull final JavaInterface javaInterface) {

    this.otpModel = otpModel;
    this.javaInterface = javaInterface;

    approveOtpDialog = new ApproveOtpDialog(context, DialogTheme, this);
    loadingScreenDialog = new LoadingScreenDialog(context, DialogTheme, this);
    loadingScreenDialogTimedOut =
        new LoadingScreenDialogTimedOut(context, DialogTheme, this);
    enterManualDialog = new EnterManualDialog(context, DialogTheme, this);
    submitDialog = new SubmitDialog(context, DialogTheme);
    countDownTimer = new CountDownTimer(30000, 1000) {
      @Override public void onTick(long l) {
        loadingScreenDialog.tick(l);
      }

      @Override public void onFinish() {
        moveToTimedOutState();
      }
    };

    baseDialog = loadingScreenDialog;
    currentScreenState = LOADING;
  }

  private void moveToTimedOutState() {
    switch (currentScreenState) {
      case LOADING:
        moveToState(LOADING_TIMEOUT);
        break;
      default:
        throw new RuntimeException(
            format("Incorrect state for timeout %1s", currentScreenState));
    }
  }

  private void moveToState(OtpScreenState otpScreenState) {
    baseDialog.detach();
    moveToNextState(otpScreenState);
    baseDialog.attach();
  }

  @Override public void attachToView() {
    baseDialog.attach();
  }

  @Override public void detachFromView() {
    baseDialog.detach();
  }

  private void moveToNextState(OtpScreenState nextState) {
    switch (currentScreenState) {
      case LOADING:
        switch (nextState) {
          case ENTER_MANUAL:
            baseDialog = enterManualDialog;
            currentScreenState = ENTER_MANUAL;
            break;
          case LOADING_TIMEOUT:
            baseDialog = loadingScreenDialogTimedOut;
            currentScreenState = LOADING_TIMEOUT;
            break;
          case APPROVE:
            baseDialog = approveOtpDialog;
            currentScreenState = APPROVE;
            break;
          default:
            throwIncorrectStateTransition(nextState);
        }
        break;
      case LOADING_TIMEOUT:
        switch (nextState) {
          case ENTER_MANUAL:
            baseDialog = enterManualDialog;
            currentScreenState = ENTER_MANUAL;
            break;
          case LOADING:
            baseDialog = loadingScreenDialog;
            currentScreenState = LOADING;
            break;
          case APPROVE:
            baseDialog = approveOtpDialog;
            currentScreenState = APPROVE;
            break;
          default:
            throwIncorrectStateTransition(nextState);
        }
        break;
      case ENTER_MANUAL:
        switch (nextState) {
          case APPROVE:
            baseDialog = approveOtpDialog;
            currentScreenState = APPROVE;
            break;
        }
        break;
      case APPROVE:
        switch (nextState) {
          case SUBMIT:
            baseDialog = submitDialog;
            currentScreenState = SUBMIT;
            break;
          default:
            throwIncorrectStateTransition(nextState);
        }
        break;
      case SUBMIT:
        switch (nextState) {
          case APPROVE:
            break;
          default:
            throwIncorrectStateTransition(nextState);
        }
      default:
        throwIncorrectStateTransition(nextState);
    }
  }

  private void throwIncorrectStateTransition(OtpScreenState nextScreenState) {
    throw new RuntimeException(
        format("Incorrect state transition from %1s to %2s", currentScreenState,
            nextScreenState));
  }

  @Override public void enterManually() {
    moveToState(ENTER_MANUAL);
  }

  @Override public void startTimer() {
    countDownTimer.start();
  }

  @Override public void stopTimer() {
    countDownTimer.cancel();
  }

  @Override public void resendOtpForLoadingScreen() {
    javaInterface.loadJavaScript(JavascriptInjectionModels.getResendOtpJavascript(otpModel));
    moveToState(LOADING);
  }

  public void setOtp(String sender, String payload) {
    if (sender == null /*|| !sender.equals(otpModel.getOtpSender())*/ || payload == null) return;
    Matcher matcher = otpModel.getPattern().matcher(payload);
    if (matcher.find()) {
      String otp = matcher.group(0);
      d("TAG", otp);
      approveOtpDialog.setOtp(otp);
      moveToState(APPROVE);
    }
  }

  @Override public void submitOtp(String otp) {
    javaInterface.loadJavaScript(getOtpSubmitJavascript(otpModel, otp));
    moveToState(SUBMIT);
  }

  enum OtpScreenState {
    LOADING, LOADING_TIMEOUT, ENTER_MANUAL, APPROVE, SUBMIT
  }

  @VisibleForTesting OtpScreenState getCurrentState() {
    return currentScreenState;
  }
}
