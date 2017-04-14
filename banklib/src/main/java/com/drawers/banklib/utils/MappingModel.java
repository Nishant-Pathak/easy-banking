package com.drawers.banklib.utils;

import com.drawers.banklib.model.BaseModel;

import java.io.Serializable;
import java.util.List;

public class MappingModel implements Serializable {
  private final String urlRegex;

  private final List<? extends BaseModel> inputTextSelectors;

  private final List<? extends BaseModel> optionSelectors;

  private final List<? extends BaseModel> buttonSelectors;

  public MappingModel(
    String urlRegex,
    List<? extends BaseModel> inputTextSelectors,
    List<? extends BaseModel> optionSelectors,
    List<? extends BaseModel> buttonSelectors
  ) {
    this.urlRegex = urlRegex;
    this.inputTextSelectors = inputTextSelectors;
    this.optionSelectors = optionSelectors;
    this.buttonSelectors = buttonSelectors;
  }
}
