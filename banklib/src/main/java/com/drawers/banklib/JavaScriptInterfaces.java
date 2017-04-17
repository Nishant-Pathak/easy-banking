package com.drawers.banklib;

import android.webkit.JavascriptInterface;

import com.drawers.banklib.events.EventListener;

import java.util.List;

/**
 * call javascript code from java
 */
public final class JavaScriptInterfaces {
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
