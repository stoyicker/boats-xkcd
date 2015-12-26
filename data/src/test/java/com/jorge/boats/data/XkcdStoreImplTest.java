package com.jorge.boats.data;

import com.jorge.boats.data.db.XkcdDatabaseHandler;
import com.jorge.boats.data.entity.DatabaseEntityMapper;
import com.jorge.boats.data.entity.DomainEntityMapper;
import com.jorge.boats.data.net.client.XkcdClient;
import com.jorge.boats.domain.repository.XkcdStore;
import com.raizlabs.android.dbflow.config.FlowManager;
import java.lang.reflect.Field;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.robolectric.RuntimeEnvironment;

public class XkcdStoreImplTest extends ApplicationTestSuite {

  private XkcdStore mSut;
  private XkcdClient mClient;
  private XkcdDatabaseHandler mXkcdDatabaseHandler;

  @Before public void setUp() {
    DataInitializer.initialize(RuntimeEnvironment.application);

    mSut = new XkcdStoreImpl(mClient = new XkcdClient(RuntimeEnvironment.application),
        new DatabaseEntityMapper(), new DomainEntityMapper(), new XkcdDatabaseHandler());
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

  @Test public void testGetStripeCurrentSuccessful() {
  }

  @Test public void testGetStripeCurrentNoConnection() {
  }

  @Test public void testGetStripeWithValidIdSuccessful() {
  }

  @Test public void testGetStripeWithInvalidIdSuccessful() {
  }

  @Test public void testGetStripeWithIdNoConnection() {
  }
}
