package com.jorge.boats;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricGradleTestRunner.class)
//TODO Change Robolectric supported SDK to 23 when available
@Config(constants = BuildConfig.class, application = StubApplication.class, sdk = 21)
public abstract class PresentationModuleTestCase {

  @Before public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @After public void tearDown() {

  }
}
