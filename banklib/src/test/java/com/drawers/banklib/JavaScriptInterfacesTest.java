package com.drawers.banklib;

import com.drawers.banklib.base.BankLibTestRunner;
import com.drawers.banklib.events.EventCode;
import com.drawers.banklib.events.EventListener;
import java.util.LinkedList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.annotation.Config;

@RunWith(BankLibTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class JavaScriptInterfacesTest {
  @Mock EventListener listener1;
  @Mock EventListener listener2;
  private final List<EventListener> listeners = new LinkedList<>();
  private static final String MOCK_EVENT_NAME = "MOCK_EVENT_NAME";

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    listeners.add(listener1);
    listeners.add(listener2);
  }

  @Test public void logSuccessEvent() throws Exception {
    JavaScriptInterfaces scriptInterfaces = new JavaScriptInterfaces(listeners);
    scriptInterfaces.logEvent(0, MOCK_EVENT_NAME);
    Mockito.verify(listener1).onEvent(EventCode.SUCCESS, MOCK_EVENT_NAME);
    Mockito.verify(listener2).onEvent(EventCode.SUCCESS, MOCK_EVENT_NAME);
  }

  @Test public void logFailureEvent() throws Exception {
    JavaScriptInterfaces scriptInterfaces = new JavaScriptInterfaces(listeners);
    scriptInterfaces.logEvent(0, MOCK_EVENT_NAME);
    Mockito.verify(listener1).onEvent(EventCode.SUCCESS, MOCK_EVENT_NAME);
    Mockito.verify(listener2).onEvent(EventCode.SUCCESS, MOCK_EVENT_NAME);
  }
}