package com.jorge.boats.data.db;

import com.jorge.boats.data.DataModuleTestSuite;
import org.junit.Before;
import org.junit.Test;

//TODO Write database tests
public class XkcdDatabaseHandlerTest extends DataModuleTestSuite {

  private XkcdDatabaseHandler mSut;

  @Before public void setUp() {
    super.setUp();

    mSut = new XkcdDatabaseHandler();
  }

  @Test public void testInsertValid() {

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
