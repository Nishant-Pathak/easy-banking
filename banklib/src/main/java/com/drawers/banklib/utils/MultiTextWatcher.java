package com.drawers.banklib.utils;

import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;

/**
 * Class used to listen for text changes on multiple EditTexts
 */

public class MultiTextWatcher {

  private TextWatcherWithInstance callback;

  public MultiTextWatcher setCallback(TextWatcherWithInstance callback) {
    this.callback = callback;
    return this;
  }

  public MultiTextWatcher registerEditText(final AppCompatEditText editText) {
    editText.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        callback.beforeTextChanged(editText, s, start, count, after);
      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        callback.onTextChanged(editText, s, start, before, count);
      }

      @Override
      public void afterTextChanged(Editable editable) {
        callback.afterTextChanged(editText, editable);
      }
    });

    return this;
  }

  public interface TextWatcherWithInstance {
    void beforeTextChanged(AppCompatEditText editText, CharSequence s, int start, int count,
      int after);

    void onTextChanged(AppCompatEditText editText, CharSequence s, int start, int before, int count);

    void afterTextChanged(AppCompatEditText editText, Editable editable);
  }
}
