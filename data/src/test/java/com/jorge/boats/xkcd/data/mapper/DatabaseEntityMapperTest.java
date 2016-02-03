package com.jorge.boats.xkcd.data.mapper;

import android.support.annotation.NonNull;
import com.jorge.boats.xkcd.data.db.DatabaseStripe;
import com.jorge.boats.xkcd.data.model.DataStripe;
import org.junit.Before;
import org.junit.Test;

import static com.jorge.boats.xkcd.data.helper.ValueGenerator.Value;
import static com.jorge.boats.xkcd.data.helper.ValueGenerator.generateLong;
import static com.jorge.boats.xkcd.data.helper.ValueGenerator.generateString;
import static org.assertj.core.api.Assertions.assertThat;

public class DatabaseEntityMapperTest {

  private DatabaseEntityMapper mSut;

  @Before public void setUp() {
    mSut = new DatabaseEntityMapper();
  }

  @Test public void testTransformFromDataNull() {
    assertThat(mSut.transform((DataStripe) null)).isNull();
  }

  @Test public void testTransformFromDatabaseNull() {
    assertThat(mSut.transform((DatabaseStripe) null)).isNull();
  }

  @Test public void testTransformFromDataAllFieldsNull() {
    final DataStripe source = new DataStripe();

    source.setAlt(generateString(Value.NULL));
    source.setDay(generateString(Value.NULL));
    source.setImg(generateString(Value.NULL));
    source.setLink(generateString(Value.NULL));
    source.setMonth(generateString(Value.NULL));
    source.setYear(generateString(Value.NULL));
    source.setNews(generateString(Value.NULL));
    source.setNum(generateLong(Value.NULL));
    source.setTitle(generateString(Value.NULL));
    source.setSafe_title(generateString(Value.NULL));
    source.setTranscript(generateString(Value.NULL));

    assertEquivalent(source, mSut.transform(source));
  }

  @Test public void testTransformFromDataAllFieldsRegular() {
    final DataStripe source = new DataStripe();

    source.setAlt(generateString(Value.REGULAR));
    source.setDay(generateString(Value.REGULAR));
    source.setImg(generateString(Value.REGULAR));
    source.setLink(generateString(Value.REGULAR));
    source.setMonth(generateString(Value.REGULAR));
    source.setYear(generateString(Value.REGULAR));
    source.setNews(generateString(Value.REGULAR));
    source.setNum(generateLong(Value.REGULAR));
    source.setTitle(generateString(Value.REGULAR));
    source.setSafe_title(generateString(Value.REGULAR));
    source.setTranscript(generateString(Value.REGULAR));

    assertEquivalent(source, mSut.transform(source));
  }

  @Test public void testTransformFromDatabaseAllFieldsNull() {
    final DatabaseStripe source = new DatabaseStripe();

    source.setAlt(generateString(Value.NULL));
    source.setDay(generateString(Value.NULL));
    source.setImg(generateString(Value.NULL));
    source.setLink(generateString(Value.NULL));
    source.setMonth(generateString(Value.NULL));
    source.setYear(generateString(Value.NULL));
    source.setNews(generateString(Value.NULL));
    source.setNum(generateLong(Value.NULL));
    source.setTitle(generateString(Value.NULL));
    source.setSafe_title(generateString(Value.NULL));
    source.setTranscript(generateString(Value.NULL));

    assertEquivalent(mSut.transform(source), source);
  }

  @Test public void testTransformFromDatabaseAllFieldsRegular() {
    final DatabaseStripe source = new DatabaseStripe();

    source.setAlt(generateString(Value.REGULAR));
    source.setDay(generateString(Value.REGULAR));
    source.setImg(generateString(Value.REGULAR));
    source.setLink(generateString(Value.REGULAR));
    source.setMonth(generateString(Value.REGULAR));
    source.setYear(generateString(Value.REGULAR));
    source.setNews(generateString(Value.REGULAR));
    source.setNum(generateLong(Value.REGULAR));
    source.setTitle(generateString(Value.REGULAR));
    source.setSafe_title(generateString(Value.REGULAR));
    source.setTranscript(generateString(Value.REGULAR));

    assertEquivalent(mSut.transform(source), source);
  }

  private void assertEquivalent(final @NonNull DataStripe dataStripe,
      final @NonNull DatabaseStripe databaseStripe) {
    assertThat(dataStripe.getAlt()).isEqualTo(databaseStripe.getAlt());
    assertThat(dataStripe.getDay()).isEqualTo(databaseStripe.getDay());
    assertThat(dataStripe.getImg()).isEqualTo(databaseStripe.getImg());
    assertThat(dataStripe.getLink()).isEqualTo(databaseStripe.getLink());
    assertThat(dataStripe.getMonth()).isEqualTo(databaseStripe.getMonth());
    assertThat(dataStripe.getYear()).isEqualTo(databaseStripe.getYear());
    assertThat(dataStripe.getNews()).isEqualTo(databaseStripe.getNews());
    assertThat(dataStripe.getNum()).isEqualTo(databaseStripe.getNum());
    assertThat(dataStripe.getTitle()).isEqualTo(databaseStripe.getTitle());
    assertThat(dataStripe.getSafe_title()).isEqualTo(databaseStripe.getSafe_title());
    assertThat(dataStripe.getTranscript()).isEqualTo(databaseStripe.getTranscript());
  }
}
