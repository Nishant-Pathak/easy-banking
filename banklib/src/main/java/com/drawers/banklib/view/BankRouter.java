package com.drawers.banklib.view;

public interface BankRouter {
  /**
   * add custom bank view to the patent
   */
  void attachToView();

  /**
   * detach view from the parent view group
   */
  void detachFromView();
}
