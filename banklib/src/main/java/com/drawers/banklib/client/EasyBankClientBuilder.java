package com.drawers.banklib.client;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.drawers.banklib.R;
import com.drawers.banklib.model.BaseModel;
import com.drawers.banklib.utils.EventListener;
import com.drawers.banklib.utils.MappingFileParser;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public final class EasyBankClientBuilder {

  private WeakReference<Context> context;
  private WeakReference<ViewGroup> parentView;

  private List<EventListener> eventListeners;

  public EasyBankClientBuilder(@NonNull Context context, @NonNull ViewGroup viewGroup) {
    this.context = new WeakReference<>(context);
    this.parentView = new WeakReference<>(viewGroup);
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

  public EasyBankClient build() throws IOException {
    MappingFileParser mappingFileParser =
      new MappingFileParser(context.get().getResources().openRawResource(R.raw.mapping));
    Map<String, BaseModel> models = mappingFileParser.parse();
    return
      new EasyBankClientImpl(
        this.context.get(),
        this.parentView.get(),
        eventListeners,
        models
      );
  }
}
