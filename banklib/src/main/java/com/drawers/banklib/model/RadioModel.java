package com.drawers.banklib.model;

import android.util.JsonReader;

import java.io.IOException;

public class RadioModel extends BaseModel {
  private final String text;

  public RadioModel(String selector, String text) {
    super(selector);
    this.text = text;
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
    return new RadioModel(selector, text);
  }
}
