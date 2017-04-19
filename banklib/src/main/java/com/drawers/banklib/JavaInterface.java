package com.drawers.banklib;

import android.support.annotation.NonNull;

/**
 * call javascript from java code
 */
public interface JavaInterface {
  /**
   * Load the javascript in {@link android.webkit.WebView}
   * @param javaScript to be loaded
   */
  void loadJavaScript(@NonNull String javaScript);
}
