package com.drawers.banklib.client;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.util.Log;
import android.webkit.WebView;
import com.drawers.banklib.JavaScriptInterfaces;
import com.drawers.banklib.R;
import com.drawers.banklib.events.EventListener;
import com.drawers.banklib.model.BaseModel;
import com.drawers.banklib.receiver.MessageBroadcastReceiver;
import com.drawers.banklib.utils.MappingFileParser;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public final class EasyBankBuilder {

  private static final String TAG = EasyBankBuilder.class.getSimpleName();
  private final List<EventListener> eventListeners;

  public EasyBankBuilder() {
    eventListeners = new LinkedList<>();
  }

  public EasyBankBuilder addEventListener(@NonNull EventListener listener) {
    this.eventListeners.add(listener);
    return this;
  }

  public EasyBankBuilder removeEventListener(@NonNull EventListener listener) {
    this.eventListeners.remove(listener);
    return this;
  }

  public EasyBankClient build(@NonNull Context context, @NonNull WebView webView) {
    Map<String, BaseModel> models = null;
    MessageBroadcastReceiver messageBroadcastReceiver = null;
    try {
      MappingFileParser mappingFileParser =
          new MappingFileParser(context.getResources().openRawResource(R.raw.mapping));
      models = mappingFileParser.parse();
      messageBroadcastReceiver = new MessageBroadcastReceiver();
    } catch (Exception ignore) {
      Log.e(TAG, "Failed to read models from stream");
    }

    if (models == null ||
        messageBroadcastReceiver == null ||
        context == null ||
        webView == null
        ) {
      Log.d(TAG, "Returning dummy client");
      return new EasyBankDummyClient();
    }

    return new EasyBankClientImpl(
        context,
        webView,
        new JavaScriptInterfaces(eventListeners),
        models,
        messageBroadcastReceiver
    );
  }

  @VisibleForTesting

  public List<EventListener> getEventListeners() {
    return eventListeners;
  }
}
