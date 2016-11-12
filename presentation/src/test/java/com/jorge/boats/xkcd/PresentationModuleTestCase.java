package com.jorge.boats.xkcd;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
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

  @Test
  public void dummy() {
    assertTrue(true);
  }
}
