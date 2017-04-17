package com.drawers.banklib.model;

import android.support.annotation.NonNull;
import android.util.JsonReader;

import com.drawers.banklib.utils.BankLibHelper;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class PaymentChoiceScreen implements BaseModel {
  private final List<RadioButtonModel> radioButtons;
  private final List<ButtonModel> buttons;

  public PaymentChoiceScreen(
    @NonNull List<RadioButtonModel> radioButtons,
    @NonNull List<ButtonModel> buttons
  ) {
    this.radioButtons = radioButtons;
    this.buttons = buttons;
  }

  public List<RadioButtonModel> getRadioButtons() {
    return radioButtons;
  }

  public List<ButtonModel> getButtons() {
    return buttons;
  }

  public static BaseModel parse(JsonReader reader) throws IOException {
    List<RadioButtonModel> radioButtonModels = null;
    List<ButtonModel> buttonModels = null;
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
        buttonModels = new LinkedList<>();
        while (reader.hasNext()) {
          buttonModels.add(ButtonModel.parse(reader));
        }
        reader.endArray();
      }
    }
    reader.endObject();
    BankLibHelper.requireNonNull(radioButtonModels, buttonModels);
    return new PaymentChoiceScreen(radioButtonModels, buttonModels);
  }
}
