package com.drawers.banklib.utils;

import android.support.annotation.Nullable;
import android.util.JsonReader;
import android.util.Log;
import android.util.Pair;

import com.drawers.banklib.model.BaseModel;
import com.drawers.banklib.model.ButtonModel;
import com.drawers.banklib.model.InputTextModel;
import com.drawers.banklib.model.RadioModel;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MappingFileParser {
  private static final String TAG = MappingFileParser.class.getSimpleName();
  private final InputStream mappingFileStream;

  public MappingFileParser(InputStream mappingFileStream) {
    this.mappingFileStream = mappingFileStream;
  }

  public @Nullable Map<String, MappingModel> parse() throws IOException {
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

  private Map<String, MappingModel> readMappingsArray(JsonReader reader) throws IOException {
    Map<String, MappingModel> mappingModels = new HashMap<>();
    reader.beginArray();
    while (reader.hasNext()) {
      Pair<String, MappingModel> urlMapping = readMapping(reader);
      mappingModels.put(urlMapping.first, urlMapping.second);
    }
    reader.endArray();
    return mappingModels;
  }

  private Pair<String, MappingModel> readMapping(JsonReader reader) throws IOException {
    List<InputTextModel> inputTextSelectors = new ArrayList<>(1);
    List<RadioModel> radioSelectors = new ArrayList<>(1);
    List<ButtonModel> buttonSelectors = new ArrayList<>(1);
    reader.beginObject();
    String urlRegex = reader.nextName();
    reader.beginObject();
    while (reader.hasNext()) {
      String name = reader.nextName();
      switch (name) {
        case "inputTextSelectors":
          inputTextSelectors = readSelectors(reader, InputTextModel.class);
          break;
        case "radioSelectors":
          radioSelectors = readSelectors(reader, RadioModel.class);
          break;
        case "buttonSelectors":
          buttonSelectors = readSelectors(reader, ButtonModel.class);
          break;
        default:
          Log.d(TAG, String.format("%s not found", name));
      }
    }
    reader.endObject();
    reader.endObject();
    return Pair.create(
      urlRegex,
      new MappingModel(urlRegex, inputTextSelectors, radioSelectors, buttonSelectors)
    );
  }

  private <T> List<T> readSelectors(JsonReader reader, Class<? extends BaseModel> modelClass) throws IOException {
    List<T> selectors = new ArrayList<>(1);
    reader.beginArray();
    while (reader.hasNext()) {
      selectors.add((T) readSelector(reader, modelClass));
    }
    reader.endArray();
    return selectors;
  }

  private <T extends BaseModel> T readSelector(
    JsonReader reader,
    Class<? extends BaseModel> modelClass
  ) throws IOException {
    BaseModel baseModel = null;
    reader.beginObject();
    if (modelClass.equals(RadioModel.class)) {
      baseModel = RadioModel.parse(reader);
    }
    if (modelClass.equals(InputTextModel.class)) {
      baseModel = InputTextModel.parse(reader);
    }
    if (modelClass.equals(ButtonModel.class)) {
      baseModel = ButtonModel.parse(reader);
    }
    reader.endObject();
    return (T) baseModel;
  }
}
