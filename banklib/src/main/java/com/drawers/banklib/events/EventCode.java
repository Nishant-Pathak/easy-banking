package com.drawers.banklib.events;

public enum EventCode {
  SUCCESS(0),
  FAILURE(1);

  public final int code;

  EventCode(int code) {
    this.code = code;
  }
}
