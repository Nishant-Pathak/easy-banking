package com.drawers.banklib.otpdialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.View;

/**
 * Submit dialog doesn't have a UI and just a state.
 */
public class SubmitView extends BaseView {

  public SubmitView(@NonNull Context context, @StyleRes int themeResId) {
    super(context, themeResId);
  }

  @Override View inflateDialogView(Context context) {
    return new View(context);
  }

  @Override void extractElements() { }

  @Override public void attach() { }

  @Override public void detach() { }
}
