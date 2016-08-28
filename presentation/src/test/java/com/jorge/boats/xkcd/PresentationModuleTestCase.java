package com.jorge.boats.xkcd;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricGradleTestRunner.class)
//TODO Change Robolectric supported SDK to 24 when available
@Config(constants = BuildConfig.class, application = StubApplication.class, sdk = 23)
public abstract class PresentationModuleTestCase {

  @Before
  protected void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @After
  public void tearDown() {

  }
}
