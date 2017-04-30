package com.drawers.banklib.base;

import com.drawers.banklib.BuildConfig;
import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricTestRunner;

public class BankLibTestRunner extends RobolectricTestRunner {

  public BankLibTestRunner(Class<?> testClass) throws InitializationError {
    super(testClass);
    String buildVariant = BuildConfig.BUILD_TYPE + (BuildConfig.FLAVOR.isEmpty()? "" : "/" + BuildConfig.FLAVOR);
    System.setProperty("android.package", BuildConfig.APPLICATION_ID);
    System.setProperty("android.manifest", "build/intermediates/manifests/aapt/" + buildVariant + "/AndroidManifest.xml");
    System.setProperty("android.resources", "build/intermediates/res/merged/" + buildVariant);
    System.setProperty("android.assets", "build/intermediates/assets/" + buildVariant);
  }
}