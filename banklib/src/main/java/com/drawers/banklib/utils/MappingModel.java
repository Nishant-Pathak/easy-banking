package com.drawers.banklib.utils;

import java.io.Serializable;
import java.util.List;

public class MappingModel implements Serializable {
  private final String urlRegex;

  private final List<Selector> inputTextSelectors;

  private final List<Selector> optionSelectors;

  private final List<Selector> buttonSelectors;

  public MappingModel(String urlRegex, List<Selector> inputTextSelectors, List<Selector> optionSelectors, List<Selector> buttonSelectors) {
    this.urlRegex = urlRegex;
    this.inputTextSelectors = inputTextSelectors;
    this.optionSelectors = optionSelectors;
    this.buttonSelectors = buttonSelectors;
  }

  public static class Selector {
    public String id;
    public String text;

    public Selector(String id, String text) {
      this.id = id;
      this.text = text;
    }
  }


}
