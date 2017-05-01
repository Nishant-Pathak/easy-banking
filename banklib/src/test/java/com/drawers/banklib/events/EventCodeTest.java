package com.drawers.banklib.events;

import com.drawers.banklib.BuildConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class EventCodeTest {

  @Test public void notNullTest() throws Exception {
    assertNotNull(EventCode.valueOf("SUCCESS"));
    assertNotNull(EventCode.valueOf("FAILURE"));
  }

  @Test public void parseInt() throws Exception {
    assertEquals(EventCode.SUCCESS, EventCode.values()[0]);
    assertEquals(EventCode.FAILURE, EventCode.values()[1]);
  }
}