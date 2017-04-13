package com.drawers.banklib.client;

import android.content.Context;
import android.support.annotation.NonNull;

import com.drawers.banklib.utils.EventListener;

import java.util.LinkedList;
import java.util.List;

public final class EasyBankClientBuilder {

  private Context context;

  private List<EventListener> eventListeners;

  public EasyBankClientBuilder(@NonNull Context context) {
    this.context = context;
    eventListeners = new LinkedList<>();
  }

  public EasyBankClientBuilder setStyles() {
    // TODO: 13/4/17 set styles for editText, ratioOptions, and button / buttonGroup
    return this;
  }

  public EasyBankClientBuilder addEventListener(@NonNull EventListener listener) {
    this.eventListeners.add(listener);
    return this;
  }

  public EasyBankClientBuilder removeEventListener(@NonNull EventListener listener) {
    this.eventListeners.remove(listener);
    return this;
  }


  public EasyBankClient build() {
    EasyBankClient bankClient = new EasyBankClientImpl(this.context, eventListeners);
    this.eventListeners = null;
    this.context = null;
    return bankClient;
  }
}
