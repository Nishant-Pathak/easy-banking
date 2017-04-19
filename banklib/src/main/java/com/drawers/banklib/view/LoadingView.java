package com.drawers.banklib.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.drawers.banklib.R;
import java.lang.ref.WeakReference;

/**
 * it is a spinner and should be shown between url load start and load finish
 */
public class LoadingView implements BankView {
  private WeakReference<View> reference;

  @Override
  public void addToView(@NonNull Context context, @NonNull ViewGroup parent) {
    // TODO: 17/4/17 show progress dialog
    final View view = LayoutInflater.from(context).inflate(R.layout.view_progress, null);
    reference = new WeakReference<>(view);
    parent.addView(view);
  }

  @Override
  public void removeFromView(@NonNull ViewGroup parent) {
    // TODO: 17/4/17 hide progress dialog
    if (reference.get() != null) {
      parent.removeView(reference.get());
    }
  }
}
