package com.drawers.banklib.model;

import android.util.JsonReader;

import java.io.IOException;

class RadioButtonModel implements BaseModel {
  private final String selector;
  private final String text;

  private RadioButtonModel(String selector, String text) {
    this.selector = selector;
    this.text = text;
  }

  public String getSelector() {
    return selector;
  }

  public String getText() {
    return text;
  }

  public static BaseModel parse(JsonReader reader) throws IOException {
    String selector = null;
    String text = null;
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
    return new RadioButtonModel(selector, text);
  }
}