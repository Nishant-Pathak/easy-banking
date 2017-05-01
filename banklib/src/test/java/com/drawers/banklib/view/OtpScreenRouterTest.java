package com.drawers.banklib.view;

import com.drawers.banklib.BuildConfig;
import com.drawers.banklib.JavaInterface;
import com.drawers.banklib.base.BankLibTestRunner;
import com.drawers.banklib.model.OtpModel;
import java.util.regex.Pattern;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.mockito.Mockito.when;

@RunWith(BankLibTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class OtpScreenRouterTest {

  @Mock JavaInterface javaInterface;
  @Mock OtpModel otpModel;

  private OtpScreenRouter otpScreenRouter;

  @Before public void setup() {
    MockitoAnnotations.initMocks(this);
    when(otpModel.getPattern()).thenReturn(Pattern.compile("\\d\\d\\d\\d\\d\\d"));
    when(otpModel.getOtpSender()).thenReturn("XYZ");
    otpScreenRouter = new OtpScreenRouter(RuntimeEnvironment.application, otpModel, javaInterface);
  }

  @Test public void setOtp_moveToStateApprove() {
    otpScreenRouter.setOtp("XYZ", "this is india 123456 what about 12 3.");
    Assert.assertEquals(OtpScreenRouter.OtpScreenState.APPROVE, otpScreenRouter.getCurrentState());
  }

  @Test public void initialise_moveToStateLoading() {
    Assert.assertEquals(OtpScreenRouter.OtpScreenState.LOADING, otpScreenRouter.getCurrentState());
  }
}