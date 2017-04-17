package com.drawers.banklib.events;

import android.webkit.JavascriptInterface;

import java.util.List;

public class JavaScriptInterfaces {
  private final List<EventListener> listeners;

  public JavaScriptInterfaces(List<EventListener> listeners) {
    this.listeners = listeners;
  }

  @JavascriptInterface
  public void logEvent(int code, String eventName) {
    for(int i = 0, size = listeners.size(); i < size; i++) {
      listeners.get(i).onEvent(code, eventName);
    }
  }
}
