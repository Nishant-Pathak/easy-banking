package com.drawers.banklib.model;

import android.support.annotation.NonNull;
import android.util.JsonReader;
import com.drawers.banklib.utils.BankLibHelper;
import java.io.IOException;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;

public class PaymentChoiceModel implements BaseModel {
  private final String TAG = PaymentChoiceModel.class.getSimpleName();
  private final EnumMap<ButtonModel.Type, ButtonModel> buttons;
  private final List<RadioButtonModel> radioButtons;

  public PaymentChoiceModel(@NonNull List<RadioButtonModel> radioButtons,
      @NonNull EnumMap<ButtonModel.Type, ButtonModel> buttons) {
    this.radioButtons = radioButtons;
    this.buttons = buttons;
  }

  public static BaseModel parse(JsonReader reader) throws IOException {
    List<RadioButtonModel> radioButtonModels = null;
    EnumMap<ButtonModel.Type, ButtonModel> buttonModels = null;
    reader.beginObject();
    while (reader.hasNext()) {
      String name = reader.nextName();
      if ("radioButtons".equals(name)) {
        reader.beginArray();
        radioButtonModels = new LinkedList<>();
        while (reader.hasNext()) {
          radioButtonModels.add(RadioButtonModel.parse(reader));
        }
        reader.endArray();
      } else if ("buttons".equals(name)) {
        reader.beginArray();
        buttonModels = new EnumMap<>(ButtonModel.Type.class);
        while (reader.hasNext()) {
          ButtonModel buttonModel = ButtonModel.parse(reader);
          buttonModels.put(buttonModel.getType(), ButtonModel.parse(reader));
        }
        reader.endArray();
      }
    }
    reader.endObject();
    BankLibHelper.requireNonNull(radioButtonModels, buttonModels);
    return new PaymentChoiceModel(radioButtonModels, buttonModels);
  }

  public List<RadioButtonModel> getRadioButtons() {
    return radioButtons;
  }

  public EnumMap<ButtonModel.Type, ButtonModel> getButtons() {
    return buttons;
  }

  @Override public String getName() {
    return TAG;
  }
}
