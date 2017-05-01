package com.drawers.banklib.model;

import android.util.JsonReader;
import android.util.Log;
import com.drawers.banklib.utils.BankLibHelper;
import java.io.IOException;
import java.util.EnumMap;

public class PasswordModel implements OtpBaseModel {
  private static final String TAG = PasswordModel.class.getSimpleName();
  private final EnumMap<ButtonModel.Type, ButtonModel> buttons;
  private final String otpInputSelector;

  public static BaseModel parse(JsonReader reader) throws IOException {
    String otpInputSelector = null;
    EnumMap<ButtonModel.Type, ButtonModel> buttonModels = null;
    reader.beginObject();
    while (reader.hasNext()) {
      String name = reader.nextName();
      switch (name) {
        case "otpInputSelector":
          otpInputSelector = reader.nextString();
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

    BankLibHelper.requireNonNull(otpInputSelector, buttonModels);
    assert otpInputSelector != null;
    assert buttonModels != null;
    return new PasswordModel(buttonModels, otpInputSelector);
  }

  private PasswordModel(EnumMap<ButtonModel.Type, ButtonModel> buttons, String otpInputSelector) {
    this.buttons = buttons;
    this.otpInputSelector = otpInputSelector;
  }

  @Override public String getName() {
    return TAG + "_" + otpInputSelector;
  }

  @Override public String getOtpInputSelector() {
    return otpInputSelector;
  }

  @Override public EnumMap<ButtonModel.Type, ButtonModel> getButtons() {
    return buttons;
  }
}
