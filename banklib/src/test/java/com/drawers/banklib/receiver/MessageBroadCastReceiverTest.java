package com.drawers.banklib.receiver;

import android.content.Context;
import android.content.Intent;

import com.drawers.banklib.BuildConfig;
import com.drawers.banklib.client.MessageListener;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class MessageBroadCastReceiverTest {

    @Mock Context context;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS) Intent intent;
    @Mock MessageListener messageListener;
    private MessageBroadcastReceiver broadcastReceiver;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        broadcastReceiver = new MessageBroadcastReceiver(messageListener);
    }

    @Test
    public void onReceive_invokeListener() {
        String pdu = "07914151551512f2040B916105551511f100006060605130308A04D4F29C0E";
        Object[] pdus = new Object[] {HexDump.hexStringToByteArray(pdu)};
        Mockito.when(intent.getExtras().get("pdus")).thenReturn(pdus);
        broadcastReceiver.onReceive(context, intent);
        Mockito.verify(messageListener).onMessageReceived("+16505551111", "Test");
    }

    // Copied partially from https://android.googlesource.com/platform/frameworks/base.git/+/master/core/java/com/android/internal/util/HexDump.java
    public static class HexDump {

        private final static char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        private final static char[] HEX_LOWER_CASE_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };


        private static int toByte(char c) {
            if (c >= '0' && c <= '9') return (c - '0');
            if (c >= 'A' && c <= 'F') return (c - 'A' + 10);
            if (c >= 'a' && c <= 'f') return (c - 'a' + 10);
            throw new RuntimeException ("Invalid hex char '" + c + "'");
        }

        public static byte[] hexStringToByteArray(String hexString)
        {
            int length = hexString.length();
            byte[] buffer = new byte[length / 2];
            for (int i = 0 ; i < length ; i += 2)
            {
                buffer[i / 2] = (byte)((toByte(hexString.charAt(i)) << 4) | toByte(hexString.charAt(i+1)));
            }
            return buffer;
        }
    }
}
