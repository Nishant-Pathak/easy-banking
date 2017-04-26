package com.drawers.banklib.model;

import android.support.annotation.NonNull;
import android.util.JsonReader;
import android.util.Log;

import com.drawers.banklib.utils.BankLibHelper;

import java.io.IOException;
import java.util.EnumMap;
import java.util.regex.Pattern;

public class OtpModel implements BaseModel {
  private static final String TAG = OtpModel.class.getSimpleName();

  private final String otpInputSelector;

  private final String label;

  private final String otpSender;

  private final String otpRegex;

  private final long waitTime;

  public Pattern getPattern() {
    return pattern;
  }

  private final Pattern pattern;

  private final EnumMap<ButtonModel.Type, ButtonModel> buttons;

  public OtpModel(
    @NonNull String otpInputSelector,
    @NonNull String label,
    @NonNull String otpSender,
    @NonNull String otpRegex,
    long waitTime,
    @NonNull EnumMap<ButtonModel.Type, ButtonModel> buttons
  ) {
    this.otpInputSelector = otpInputSelector;
    this.label = label;
    this.otpSender = otpSender;
    this.otpRegex = otpRegex;
    this.waitTime = waitTime;
    this.buttons = buttons;
    pattern = Pattern.compile(this.otpRegex);
  }

  public static BaseModel parse(JsonReader reader) throws IOException {
    String otpInputSelector = null;
    String label = null;
    String otpSender = null;
    String otpRegex = null;
    long waitTime = 0;
    EnumMap<ButtonModel.Type, ButtonModel> buttonModels = null;
    reader.beginObject();
    while (reader.hasNext()) {
      String name = reader.nextName();
      switch (name) {
        case "otpInputSelector":
          otpInputSelector = reader.nextString();
          break;
        case "label":
          label = reader.nextString();
          break;
        case "otpSender":
          otpSender = reader.nextString();
          break;
        case "otpRegex":
          otpRegex = reader.nextString();
          break;
        case "waitTime":
          waitTime = reader.nextLong();
          break;
        case "buttons":
          reader.beginArray();
          buttonModels = new EnumMap<>(ButtonModel.Type.class);
          while (reader.hasNext()) {
            ButtonModel buttonModel = ButtonModel.parse(reader);
            buttonModels.put(buttonModel.getType(), buttonModel);
          }
          reader.endArray();
          break;
        default:
          Log.d(TAG, String.format("%s not found", name));
          break;
      }
    }
    reader.endObject();

    BankLibHelper.requireNonNull(otpInputSelector, otpSender, otpRegex, buttonModels);
    assert otpInputSelector != null;
    assert label != null;
    assert otpSender != null;
    assert otpRegex != null;
    assert buttonModels != null;
    return new OtpModel(otpInputSelector, label, otpSender, otpRegex, waitTime, buttonModels);
  }

  public String getOtpInputSelector() {
    return otpInputSelector;
  }

  public String getLabel() {
    return label;
  }

  public String getOtpSender() {
    return otpSender;
  }

  public String getOtpRegex() {
    return otpRegex;
  }

  public long getWaitTime() {
    return waitTime;
  }

  public EnumMap<ButtonModel.Type, ButtonModel> getButtons() {
    return buttons;
  }

  @Override
  public String getName() {
    return TAG + "_" + otpRegex;
  }
}
