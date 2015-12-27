package com.jorge.boats.data;

import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricGradleTestRunner.class)
//TODO Change Robolectric supported SDK to 23 when available
@Config(constants = BuildConfig.class, application = StubApplication.class, sdk = 21)
abstract class ApplicationTestSuite {
}
