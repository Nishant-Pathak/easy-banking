package com.drawers.banklib.model;

import android.util.JsonReader;
import com.drawers.banklib.utils.BankLibHelper;
import java.io.IOException;

class RadioButtonModel implements BaseModel {
  private final String TAG = RadioButtonModel.class.getSimpleName();
  private final String selector;
  private final String text;

  private RadioButtonModel(String selector, String text) {
    this.selector = selector;
    this.text = text;
  }

  public static RadioButtonModel parse(JsonReader reader) throws IOException {
    String selector = null;
    String text = null;
    reader.beginObject();
    while (reader.hasNext()) {
      String name = reader.nextName();
      if ("selector".equals(name)) {
        selector = reader.nextString();
      } else if ("text".equals(name)) {
        text = reader.nextString();
      }
    }
    reader.endObject();
    BankLibHelper.requireNonNull(selector, text);
    return new RadioButtonModel(selector, text);
  }

  public String getSelector() {
    return selector;
  }

  public String getText() {
    return text;
  }

  @Override public String getName() {
    return TAG;
  }
}
