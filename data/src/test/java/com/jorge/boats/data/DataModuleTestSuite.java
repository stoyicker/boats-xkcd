package com.jorge.boats.data;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

@RunWith(RobolectricGradleTestRunner.class)
//TODO Change Robolectric supported SDK to 23 when available
@Config(constants = BuildConfig.class, application = StubApplication.class, sdk = 21)
public abstract class DataModuleTestSuite {

  @Before public void setUp() {
    DataManager.initialize(RuntimeEnvironment.application);
  }

  @After public void tearDown() {
    DataManager.destroy();
  }
}
