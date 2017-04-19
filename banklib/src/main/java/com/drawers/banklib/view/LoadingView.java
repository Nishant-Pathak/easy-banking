package com.drawers.banklib.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.drawers.banklib.R;

/**
 * it is a spinner and should be shown between url load start and load finish
 */
public class LoadingView implements BankView {
  private View loadingView;

  /**
   * {@inheritDoc}
   */
  @Override
  public void attachToView(@NonNull Context context, @NonNull ViewGroup parent) {
    loadingView = LayoutInflater.from(context).inflate(R.layout.view_progress, null);
    parent.addView(loadingView);
  }

  @Override
  public void detachFromView(@NonNull ViewGroup parent) {
    if (loadingView != null) {
      parent.removeView(loadingView);
    }
  }
}
