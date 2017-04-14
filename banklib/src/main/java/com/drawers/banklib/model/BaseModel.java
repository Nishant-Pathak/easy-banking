package com.drawers.banklib.model;

public abstract class BaseModel {
  private final String selector;

  public BaseModel(String selector) {
    this.selector = selector;
  }

  public String getSelector() {
    return selector;
  }
}
