package com.drawers.banklib.model;

import com.drawers.banklib.BuildConfig;
import com.drawers.banklib.base.BankLibTestRunner;
import java.util.EnumMap;
import java.util.regex.Pattern;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;

@RunWith(BankLibTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class OtpModelTest {
  private OtpModel otpModel;
  private final static String MOCK_INPUT_SELECTOR = "MOCK__INPUT_SELECTOR";
  private final static String MOCK_OTP_SENDER = "MOCK__INPUT_LABEL";
  private final static String MOCK_OTP_REGEX = "MOCK_OTP_REGEX";

  private EnumMap<ButtonModel.Type, ButtonModel> buttonMap;

  @Mock
  private ButtonModel buttonModel;

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    buttonMap = new EnumMap<>(ButtonModel.Type.class);
    buttonMap.put(ButtonModel.Type.SUBMIT, buttonModel);

    otpModel = new OtpModel(
        MOCK_INPUT_SELECTOR,
        MOCK_OTP_SENDER,
        MOCK_OTP_REGEX,
        buttonMap
    );
  }

  @Test public void parse() throws Exception {

  }

  @Test public void getPattern() throws Exception {
    Pattern expectedPattern = Pattern.compile(MOCK_OTP_REGEX);
    assertEquals(expectedPattern.pattern(), otpModel.getPattern().pattern());
  }

  @Test public void getOtpInputSelector() throws Exception {
    assertEquals(MOCK_INPUT_SELECTOR, otpModel.getOtpInputSelector());
  }

  @Test public void getOtpSender() throws Exception {
    assertEquals(MOCK_OTP_SENDER, otpModel.getOtpSender());
  }

  @Test public void getOtpRegex() throws Exception {
    assertEquals(MOCK_OTP_REGEX, otpModel.getOtpRegex());
  }

  @Test public void getButtons() throws Exception {
    assertEquals(buttonMap, otpModel.getButtons());
  }

  @Test public void getName() throws Exception {
    assertEquals("OtpModel_MOCK__INPUT_SELECTOR", otpModel.getName());
  }
}