package com.drawers.banklib.utils;

import android.support.annotation.Nullable;
import android.util.JsonReader;
import android.util.Log;
import android.util.Pair;

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
        reader.close();
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
    List<MappingModel.Selector> inputTextSelectors = new ArrayList<>(1);
    List<MappingModel.Selector> optionSelectors = new ArrayList<>(1);
    List<MappingModel.Selector> buttonSelectors = new ArrayList<>(1);
    reader.beginObject();
    String urlRegex = reader.nextName();
    reader.beginObject();
    while (reader.hasNext()) {
      String name = reader.nextName();
      switch (name) {
        case "inputTextSelectors":
          inputTextSelectors = readSelectors(reader);
          break;
        case "optionSelectors":
          optionSelectors = readSelectors(reader);
          break;
        case "buttonSelectors":
          buttonSelectors = readSelectors(reader);
          break;
        default:
          Log.d(TAG, String.format("%s not found", name));
      }
    }
    reader.endObject();
    reader.endObject();
    return Pair.create(
      urlRegex,
      new MappingModel(urlRegex, inputTextSelectors, optionSelectors, buttonSelectors)
    );
  }

  private List<MappingModel.Selector> readSelectors(JsonReader reader) throws IOException {
    List<MappingModel.Selector> selectors = new ArrayList<>(1);
    reader.beginArray();
    while (reader.hasNext()) {
      selectors.add(readSelector(reader));
    }
    reader.endArray();
    return selectors;
  }

  private MappingModel.Selector readSelector(JsonReader reader) throws IOException {
    String id = null;
    String text = null;
    reader.beginObject();
    while (reader.hasNext()) {
      String name = reader.nextName();
      if ("id".equals(name)) {
        id = reader.nextString();
      } else if ("text".equals(name)) {
        text = reader.nextString();
      }

    }
    reader.endObject();
    return new MappingModel.Selector(id, text);
  }
}
