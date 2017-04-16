package com.drawers.banklib.model;

import android.support.annotation.NonNull;
import android.util.JsonReader;
import android.util.Log;

import com.drawers.banklib.utils.BankLibHelper;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class OtpScreenModel implements BaseModel {
  private static final String TAG = OtpScreenModel.class.getSimpleName();
  private final String otpInputSelector;
  private final String label;
  private final String otpSender;
  private final String otpRegex;
  private final long waitTime;

  private final List<ButtonModel> buttons;

  public OtpScreenModel(
    @NonNull String otpInputSelector,
    @NonNull String label,
    @NonNull String otpSender,
    @NonNull String otpRegex,
    long waitTime,
    @NonNull List<ButtonModel> buttons
  ) {
    this.otpInputSelector = otpInputSelector;
    this.label = label;
    this.otpSender = otpSender;
    this.otpRegex = otpRegex;
    this.waitTime = waitTime;
    this.buttons = buttons;
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

  public List<ButtonModel> getButtons() {
    return buttons;
  }

  public static BaseModel parse(JsonReader reader) throws IOException {
    String otpInputSelector = null;
    String label = null;
    String otpSender = null;
    String otpRegex = null;
    long waitTime = 0;
    List<ButtonModel> buttonModels = null;
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
          buttonModels = new LinkedList<>();
          while (reader.hasNext()) {
            buttonModels.add(ButtonModel.parse(reader));
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
    return new OtpScreenModel(otpInputSelector, label, otpSender, otpRegex, waitTime, buttonModels);
  }

}
