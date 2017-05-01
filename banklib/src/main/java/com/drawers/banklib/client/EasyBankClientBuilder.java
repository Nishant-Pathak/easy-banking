package com.drawers.banklib.client;

import android.content.Context;
import android.support.annotation.NonNull;
import android.webkit.WebView;
import com.drawers.banklib.JavaScriptInterfaces;
import com.drawers.banklib.R;
import com.drawers.banklib.events.EventListener;
import com.drawers.banklib.model.BaseModel;
import com.drawers.banklib.receiver.MessageBroadcastReceiver;
import com.drawers.banklib.utils.MappingFileParser;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public final class EasyBankClientBuilder {

  private final WeakReference<Context> context;
  private final WeakReference<WebView> webView;
  private final List<EventListener> eventListeners;

  public EasyBankClientBuilder(@NonNull Context context, @NonNull WebView webView) {
    this.context = new WeakReference<>(context);
    this.webView = new WeakReference<>(webView);
    eventListeners = new LinkedList<>();
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
    MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();

    if (models == null) {
      throw new IOException("Not able to parse json file");
    }
    return new EasyBankClientImpl(
        context.get(),
        webView.get(),
        new JavaScriptInterfaces(eventListeners),
        models,
        messageBroadcastReceiver
    );
  }
}
