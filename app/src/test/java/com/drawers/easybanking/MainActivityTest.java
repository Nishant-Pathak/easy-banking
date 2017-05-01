package com.drawers.easybanking;

import android.webkit.WebView;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(
    constants = BuildConfig.class,
    sdk = 21
)
public class MainActivityTest {

  MainActivity mainActivity;

  @Before
  public void setup() {
    mainActivity = Robolectric.setupActivity(MainActivity.class);
  }

  @Test
  public void viewsSetupCorrectly() throws Exception {
    WebView webView = (WebView) mainActivity.findViewById(R.id.web_view);
    Assert.assertNotNull(webView);
    Assert.assertEquals(true, webView.getSettings().getJavaScriptEnabled());
  }
}