package com.drawers.banklib.model;

import android.support.annotation.NonNull;
import android.util.JsonReader;

import java.io.IOException;
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
    // TODO: 16/4/17 parse reader and throw IOException in case of failure
    return null;
  }
}
