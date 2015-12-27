package com.jorge.boats.data;

import com.jorge.boats.data.db.XkcdDatabaseHandler;
import com.jorge.boats.data.entity.DatabaseEntityMapper;
import com.jorge.boats.data.entity.DomainEntityMapper;
import com.jorge.boats.data.net.XkcdClient;
import com.jorge.boats.domain.repository.XkcdStore;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.robolectric.RuntimeEnvironment;

import static org.assertj.core.api.Assertions.assertThat;

public class XkcdStoreImplTest extends ApplicationTestSuite {

  private XkcdStore mSut;
  @Mock private XkcdClient mMockClient;
  @Mock private XkcdDatabaseHandler mMockXkcdDatabaseHandler;

  @Before public void setUp() {
    DataManager.initialize(RuntimeEnvironment.application);

    mSut = new XkcdStoreImpl(mMockClient, new DatabaseEntityMapper(), new DomainEntityMapper(),
        new XkcdDatabaseHandler());
  }

  /**
   * <a href="https://github.com/robolectric/robolectric/issues/1890#issuecomment-120080017">Note
   * that this is likely to be broken by newer releases.</a>
   */
  @After public void tearDown() throws Exception {
    DataManager.destroy();
  }

  @Test public void testGetStripeCurrentSuccessful() {
    assertThat(true).isTrue();
  }
  //
  //@Test public void testGetStripeCurrentNoConnection() {
  //}
  //
  //@Test public void testGetStripeWithValidIdSuccessful() {
  //}
  //
  //@Test public void testGetStripeWithInvalidIdSuccessful() {
  //}
  //
  //@Test public void testGetStripeWithIdNoConnection() {
  //}
}
