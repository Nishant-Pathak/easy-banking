package com.drawers.banklib.model;

import android.util.JsonReader;

import java.io.IOException;

public class ButtonModel implements BaseModel {
  private final String selector;
  private final String text;

  public ButtonModel(String selector, String text) {
    this.selector = selector;
    this.text = text;
  }

  public String getSelector() {
    return selector;
  }

  public String getText() {
    return text;
  }

  public static ButtonModel parse(JsonReader reader) throws IOException {
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
    if (selector == null || text == null) {
      throw new IOException();
    }
    reader.endObject();
    return new ButtonModel(selector, text);
  }
}
