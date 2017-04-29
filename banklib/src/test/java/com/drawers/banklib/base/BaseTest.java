package com.drawers.banklib.base;

import com.drawers.banklib.BuildConfig;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

@RunWith(BankLibTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21 )
public class BaseTest {

}
