package com.jorge.boats.data.db;

import com.jorge.boats.data.DataModuleTestSuite;
import com.jorge.boats.data.helper.ValueGenerator;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

//TODO Write database tests
public class XkcdDatabaseHandlerTest extends DataModuleTestSuite {

  private XkcdDatabaseHandler mSut;

  @Before public void setUp() {
    super.setUp();

    mSut = new XkcdDatabaseHandler();
  }

  @Test public void testInsertValid() {
    final DatabaseStripe stripe = ValueGenerator.generateRandomDatabaseStripe();

    mSut.insertStripe(stripe);

    assertThat(SQLite.select().from(DatabaseStripe.class).query().getCount()).isEqualTo(1);
  }

  @Test public void testInsertRepeatedPrimaryKey() {

  }

  @Test public void testInsertNull() {

  }

  @Test public void testSelectFromEmpty() {

  }

  @Test public void testSelectFromNonEmptyNoMatches() {

  }

  @Test public void testSelectMatchingFromSize1() {

  }

  @Test public void testSelectMatchingAmongSeveral() {

  }
}
