package com.jorge.boats.data;

import com.raizlabs.android.dbflow.config.FlowManager;
import java.lang.reflect.Field;
import org.junit.After;
import org.junit.Before;

public class XkcdStoreImplTest extends ApplicationTestCase {

  private XkcdStoreImplTest mSut;

  @Before public void setUp() {
  }

  /**
   * <a href="https://github.com/robolectric/robolectric/issues/1890#issuecomment-120080017">Note
   * that this is likely to be broken by newer releases.</a>
   */
  @After public void tearDown() throws Exception {
    final Field field = FlowManager.class.getDeclaredField("mDatabaseHolder");
    final boolean wasAccessible = field.isAccessible();

    if (!wasAccessible) field.setAccessible(true);
    field.set(null, null);
    field.setAccessible(wasAccessible);
  }
}
