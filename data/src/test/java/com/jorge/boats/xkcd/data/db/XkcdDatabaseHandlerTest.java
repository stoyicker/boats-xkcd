package com.jorge.boats.xkcd.data.db;

import android.text.TextUtils;

import com.jorge.boats.xkcd.data.DataModuleTestCase;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.assertj.core.api.Condition;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static com.jorge.boats.xkcd.data.helper.ValueGenerator.Value;
import static com.jorge.boats.xkcd.data.helper.ValueGenerator.generateLong;
import static com.jorge.boats.xkcd.data.helper.ValueGenerator.generateRandomDatabaseStripe;
import static org.assertj.core.api.Assertions.assertThat;

public class XkcdDatabaseHandlerTest extends DataModuleTestCase {

  private XkcdDatabaseHandler mSut;

  @Rule public final ExpectedException mExceptionExpectation = ExpectedException.none();

  @Before
  public void setUp() {
    super.setUp();

    mSut = new XkcdDatabaseHandler();
  }

  @Test
  public void testInsertValid() {
    final DatabaseStripe stripe = generateRandomDatabaseStripe();

    mSut.insertStripe(stripe);

    assertThat(SQLite.select().from(DatabaseStripe.class).query().getCount()).isEqualTo(1);
  }

  @Test
  public void testInsertRepeatedPrimaryKey() {
    final DatabaseStripe oldStripe = generateRandomDatabaseStripe(), newStripe =
        generateRandomDatabaseStripe();

    mSut.insertStripe(oldStripe);

    assertThat(SQLite.select().from(DatabaseStripe.class).query().getCount()).isEqualTo(1);

    newStripe.setNum(oldStripe.getNum());

    final Condition<DatabaseStripe> equivalentToOldStripe = new Condition<DatabaseStripe>() {
      @Override
      public boolean matches(final DatabaseStripe value) {
        return TextUtils.equals(oldStripe.getMonth(), value.getMonth())
            && TextUtils.equals(oldStripe.getLink(), value.getLink())
            && TextUtils.equals(oldStripe.getYear(), value.getYear())
            && TextUtils.equals(oldStripe.getNews(), value.getNews())
            && TextUtils.equals(oldStripe.getSafe_title(), value.getSafe_title())
            && TextUtils.equals(oldStripe.getTranscript(), value.getTranscript())
            && TextUtils.equals(oldStripe.getAlt(), value.getAlt())
            && TextUtils.equals(oldStripe.getImg(), value.getImg())
            && TextUtils.equals(oldStripe.getDay(), value.getDay());
      }
    };

    assertThat(newStripe).isNot(equivalentToOldStripe);

    mSut.insertStripe(newStripe);

    assertThat(SQLite.select().from(DatabaseStripe.class).query().getCount()).isEqualTo(1);

    assertThat(mSut.queryForStripeWithNum(newStripe.getNum())).isEqualToComparingFieldByField(
        newStripe);
  }

  @Test
  public void testSelectFromEmpty() {
    assertThat(mSut.queryForStripeWithNum(generateLong(Value.REGULAR))).isNull();
  }

  @Test
  public void testSelectFromNonEmptyNoMatches() {
    final DatabaseStripe stripeOne = generateRandomDatabaseStripe(), stripeTwo =
        generateRandomDatabaseStripe();
    final long numberOne = 10, numberTwo = 12, numberThree = 13;

    stripeOne.setNum(numberOne);
    mSut.insertStripe(stripeOne);
    stripeTwo.setNum(numberTwo);
    mSut.insertStripe(stripeTwo);

    assertThat(mSut.queryForStripeWithNum(numberThree)).isNull();
  }

  @Test
  public void testSelectMatchingFromSize1() {
    final DatabaseStripe stripe = generateRandomDatabaseStripe();

    mSut.insertStripe(stripe);

    assertThat(mSut.queryForStripeWithNum(stripe.getNum())).isEqualToComparingFieldByField(stripe);
  }

  @Test
  public void testSelectMatchingFromSizeSeveral() {
    final DatabaseStripe targetStripe = generateRandomDatabaseStripe(), extraStripeOne =
        generateRandomDatabaseStripe(), extraStripeTwo = generateRandomDatabaseStripe(),
        extraStripeThree = generateRandomDatabaseStripe();
    final long idOne = 1, idTwo = 2, idThree = 3, idFour = 4;

    targetStripe.setNum(idOne);
    mSut.insertStripe(targetStripe);
    extraStripeOne.setNum(idTwo);
    mSut.insertStripe(extraStripeOne);
    extraStripeTwo.setNum(idThree);
    mSut.insertStripe(extraStripeTwo);
    extraStripeThree.setNum(idFour);
    mSut.insertStripe(extraStripeThree);

    assertThat(mSut.queryForStripeWithNum(targetStripe.getNum())).isEqualToComparingFieldByField(
        targetStripe);
  }
}
