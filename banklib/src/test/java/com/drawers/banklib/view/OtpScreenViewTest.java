package com.drawers.banklib.view;

import com.drawers.banklib.JavaInterface;
import com.drawers.banklib.base.BaseTest;
import com.drawers.banklib.model.OtpModel;
import java.util.regex.Pattern;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RuntimeEnvironment;

import static org.mockito.Mockito.when;

public class OtpScreenViewTest extends BaseTest {

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
    OtpScreenView otpScreenView = new OtpScreenView(RuntimeEnvironment.application, otpModel, javaInterface);
    otpScreenView.setOtp("XYZ", "this is india 123456 what about 12 3.");
  }
}