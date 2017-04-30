package com.drawers.banklib.view;

import com.drawers.banklib.otpdialog.BaseDialog;

public interface BankView {
  /**
   * add custom bank view to the patent
   */
  void attachToView();

  /**
   * detach view from the parent view group
   */
  void detachFromView();
}
