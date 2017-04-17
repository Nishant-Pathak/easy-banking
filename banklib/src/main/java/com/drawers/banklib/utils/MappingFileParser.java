package com.drawers.banklib.utils;

import android.support.annotation.Nullable;
import android.util.JsonReader;
import android.util.Log;
import android.util.Pair;

import com.drawers.banklib.model.BaseModel;
import com.drawers.banklib.model.OtpModel;
import com.drawers.banklib.model.PaymentChoiceModel;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class MappingFileParser {
  private static final String TAG = MappingFileParser.class.getSimpleName();
  private final InputStream mappingFileStream;

  public MappingFileParser(InputStream mappingFileStream) {
    this.mappingFileStream = mappingFileStream;
  }

  public @Nullable Map<String, BaseModel> parse() throws IOException {
    JsonReader reader = null;
    try {
      reader = new JsonReader(new InputStreamReader(mappingFileStream, "UTF-8"));
      return readMappingsArray(reader);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } finally {
      if (reader != null) {
        try {
          reader.close();
        } catch (Exception ignore) {}
      }
    }
    return null;
  }

  private Map<String, BaseModel> readMappingsArray(JsonReader reader) throws IOException {
    Map<String, BaseModel> mappingModels = new HashMap<>();
    reader.beginArray();
    while (reader.hasNext()) {
      Pair<String, ? extends BaseModel> urlMapping = readMapping(reader);
      mappingModels.put(urlMapping.first, urlMapping.second);
    }
    reader.endArray();
    return mappingModels;
  }

  private Pair<String, ? extends BaseModel> readMapping(JsonReader reader) throws IOException {
    reader.beginObject();
    BaseModel model = null;
    String urlRegex = reader.nextName();
    Log.d(TAG, String.format("got regex for: %s", urlRegex));
    reader.beginObject();
    while (reader.hasNext()) {
      String name = reader.nextName();
      switch (name) {
        case "otpScreen":
          model = OtpModel.parse(reader);
          break;
        case "paymentChoiceScreen":
          model = PaymentChoiceModel.parse(reader);
          break;
        default:
          Log.d(TAG, String.format("%s not found", name));
      }
    }
    reader.endObject();
    reader.endObject();
    return Pair.create(urlRegex, model);
  }
}
