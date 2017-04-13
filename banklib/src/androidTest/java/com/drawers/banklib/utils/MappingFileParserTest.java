package com.drawers.banklib.utils;

import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.InputStream;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class MappingFileParserTest {

  @Test
  public void parse() throws Exception {
    InputStream resource = MappingFileParserTest.class.getClassLoader().getResourceAsStream("sample1.json");
    MappingFileParser parser = new MappingFileParser(resource);
    Map<String, MappingModel> parse = parser.parse();
    assertNotNull(parse);
  }
}