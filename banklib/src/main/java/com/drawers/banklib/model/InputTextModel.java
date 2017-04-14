package com.drawers.banklib.model;

import android.util.JsonReader;

import java.io.IOException;

public class InputTextModel extends BaseModel {
  private final String label;

  private final String otpSender;

  private final String otpRegex;


  public InputTextModel(String selector, String label, String otpSender, String otpRegex) {
    super(selector);
    this.label = label;
    this.otpSender = otpSender;
    this.otpRegex = otpRegex;
  }

  public static BaseModel parse(JsonReader reader) throws IOException {
    String selector = null;
    String label = null;
    String otpSender = null;
    String otpRegex = null;
    while (reader.hasNext()) {
      String name = reader.nextName();
      if ("selector".equals(name)) {
        selector = reader.nextString();
      } else if ("label".equals(name)) {
        label = reader.nextString();
      } else if ("otpSender".equals(name)) {
        otpSender = reader.nextString();
      } else if ("otpRegex".equals(name)) {
        otpRegex = reader.nextString();

      }
    }
    if (selector == null || label == null || otpSender == null || otpRegex == null) {
      throw new IOException();
    }
    return new InputTextModel(selector, label, otpSender, otpRegex);
  }
}
