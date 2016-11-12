package com.jorge.boats.xkcd.data;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
//TODO Change Robolectric supported SDK to 24 when available
@Config(constants = BuildConfig.class, application = StubApplication.class, sdk = 23)
public abstract class DataModuleTestCase {

  @Before
  public void setUp() {
    DataManager.initialize(RuntimeEnvironment.application);
    MockitoAnnotations.initMocks(this);
  }

  @After
  public void tearDown() {
    DataManager.destroy();
  }
}
