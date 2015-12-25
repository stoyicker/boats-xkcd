package com.jorge.boats.data.entity;

import com.jorge.boats.data.db.DatabaseStripe;
import com.jorge.boats.data.model.DataStripe;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Test;

import static com.jorge.boats.data.entity.ValueGenerator.Value;
import static com.jorge.boats.data.entity.ValueGenerator.generateLong;
import static com.jorge.boats.data.entity.ValueGenerator.generateString;
import static org.assertj.core.api.Assertions.assertThat;

public class DatabaseEntityMapperTest {

  private DatabaseEntityMapper mSut;

  @Before public void setUp() {
    mSut = new DatabaseEntityMapper();
  }

  @Test public void testFromDataTransformNull() {
    assertThat(mSut.transform((DataStripe) null)).isNull();
  }

  @Test public void testFromDatabaseTransformNull() {
    assertThat(mSut.transform((DatabaseStripe) null)).isNull();
  }

  @Test public void testFromDataTransformAllFieldsNull() {
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

  @Test public void testFromDataTransformAllFieldsRegular() {
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

  @Test public void testFromDatabaseTransformAllFieldsNull() {
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

  @Test public void testFromDatabaseTransformAllFieldsRegular() {
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

  private void assertEquivalent(final @NotNull DataStripe dataStripe,
      final @NotNull DatabaseStripe databaseStripe) {
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
