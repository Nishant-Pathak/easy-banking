package com.drawers.banklib.view;

import com.drawers.banklib.otpdialog.BaseDialog;

public abstract class BankView {
  /**
   * add custom bank view to the patent
   */
  public void attachToView() {
    getCurrentDialog().show();
  }

  /**
   * detach view from the parent view group
   */
  public void detachFromView() {
    getCurrentDialog().dismiss();
  }

  abstract BaseDialog getCurrentDialog();
}
