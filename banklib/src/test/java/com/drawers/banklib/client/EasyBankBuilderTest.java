package com.drawers.banklib.client;

import android.content.Context;
import android.webkit.WebView;
import com.drawers.banklib.BuildConfig;
import com.drawers.banklib.base.BankLibTestRunner;
import com.drawers.banklib.events.EventListener;
import java.io.InputStream;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.annotation.Config;

@RunWith(BankLibTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class EasyBankBuilderTest {

  @Mock Context context;
  @Mock WebView webView;
  @Mock InputStream inputStream;
  @Mock EventListener listener;

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
  }

  @Test public void addEventListener() throws Exception {
    EasyBankBuilder builder = new EasyBankBuilder();
    builder.addEventListener(listener);
    Assert.assertTrue(builder.getEventListeners().contains(listener));
  }

  @Test public void removeEventListener() throws Exception {
    EasyBankBuilder builder = new EasyBankBuilder();
    builder.addEventListener(listener);
    builder.removeEventListener(listener);
    Assert.assertFalse(builder.getEventListeners().contains(listener));
  }

  /*
  @Test public void build() throws Exception {
  }
  */

  @Test public void build_dummyClientIfSomethingWentWrong() throws Exception {
    Assert.assertThat(
        new EasyBankBuilder().build(context, webView),
        CoreMatchers.instanceOf(EasyBankDummyClient.class)
    );
  }

  @Test public void build_dummyClientIfInputAreNull() throws Exception {
    Assert.assertThat(
        new EasyBankBuilder().build(null, webView),
        CoreMatchers.instanceOf(EasyBankDummyClient.class)
    );
    Assert.assertThat(
        new EasyBankBuilder().build(context, null),
        CoreMatchers.instanceOf(EasyBankDummyClient.class)
    );
    Assert.assertThat(
        new EasyBankBuilder().build(null, null),
        CoreMatchers.instanceOf(EasyBankDummyClient.class)
    );
  }

}