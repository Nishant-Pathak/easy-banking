package com.drawers.banklib.view;

import com.drawers.banklib.BuildConfig;
import com.drawers.banklib.JavaInterface;
import com.drawers.banklib.model.OtpModel;
import java.util.regex.Pattern;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.mockito.Mockito.when;

@RunWith(RobolectricGradleTestRunner.class)
@Config(
    constants = BuildConfig.class,
    sdk = 21
)
public class OtpScreenViewTest {

  @Mock JavaInterface javaInterface;
  @Mock OtpModel otpModel;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    when(otpModel.getPattern()).thenReturn(Pattern.compile("\\d\\d\\d\\d\\d\\d"));
    when(otpModel.getOtpSender()).thenReturn("XYZ");
  }

  @Test
  public void setOtp() throws Exception {
    OtpScreenView otpScreenView = new OtpScreenView(otpModel, javaInterface, RuntimeEnvironment.application);
    otpScreenView.setOtp("XYZ", "this is india 123456 what about 12 3.");
  }
}