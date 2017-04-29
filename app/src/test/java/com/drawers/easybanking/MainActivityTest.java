package com.drawers.easybanking;

import android.webkit.WebView;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 25)
public class MainActivityTest {

  MainActivity mainActivity;

  @Before
  public void setup() {
    mainActivity = Robolectric.setupActivity(MainActivity.class);
  }

  @Test
  public void viewSetupCorrectly() throws Exception {
    WebView webView = (WebView) mainActivity.findViewById(R.id.web_view);
    Assert.assertNotNull(webView);
  }
}