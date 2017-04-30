package com.drawers.banklib.model;

import android.util.JsonReader;
import com.drawers.banklib.utils.BankLibHelper;
import java.io.IOException;

public class ButtonModel implements BaseModel {
  private final String TAG = ButtonModel.class.getSimpleName();
  private final String selector;
  private final String text;
  private final Type type;

  public ButtonModel(Type type, String selector, String text) {
    this.type = type;
    this.selector = selector;
    this.text = text;
  }

  public static ButtonModel parse(JsonReader reader) throws IOException {
    Type type = null;
    String selector = null;
    String text = null;
    reader.beginObject();
    while (reader.hasNext()) {
      String name = reader.nextName();
      if ("type".equals(name)) {
        type = Type.valueOf(reader.nextString());
      } else if ("selector".equals(name)) {
        selector = reader.nextString();
      } else if ("text".equals(name)) {
        text = reader.nextString();
      }
    }
    reader.endObject();
    BankLibHelper.requireNonNull(type, selector, text);
    return new ButtonModel(type, selector, text);
  }

  public String getSelector() {
    return selector;
  }

  public String getText() {
    return text;
  }

  public Type getType() {
    return type;
  }

  @Override public String getName() {
    return TAG + "_" + selector;
  }

  public enum Type {
    SUBMIT,
    CANCEL,
    RESEND_OTP1,
    RESEND_OTP2,
    RESEND_OTP
  }
}
