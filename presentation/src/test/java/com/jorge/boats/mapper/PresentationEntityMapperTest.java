package com.jorge.boats.mapper;

import android.support.annotation.NonNull;
import com.jorge.boats.domain.entity.DomainStripe;
import com.jorge.boats.entity.PresentationStripe;
import org.junit.Before;
import org.junit.Test;

import static com.jorge.boats.helper.ValueGenerator.Value;
import static com.jorge.boats.helper.ValueGenerator.generateLong;
import static com.jorge.boats.helper.ValueGenerator.generateString;
import static org.assertj.core.api.Assertions.assertThat;

public class PresentationEntityMapperTest {

  private PresentationEntityMapper mSut;

  @Before public void setUp() {
    mSut = new PresentationEntityMapper();
  }

  @Test public void testTransformFromDomainNull() {
    assertThat(mSut.transform((DomainStripe) null)).isNull();
  }

  @Test public void testTransformFromPresentationNull() {
    assertThat(mSut.transform((PresentationStripe) null)).isNull();
  }

  @Test public void testTransformFromDomainAllFieldsNull() {
    final DomainStripe source = new DomainStripe();

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

  @Test public void testTransformFromDomainAllFieldsRegular() {
    final DomainStripe source = new DomainStripe();

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

  @Test public void testTransformFromPresentationAllFieldsNull() {
    final PresentationStripe source = new PresentationStripe();

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

  @Test public void testTransformFromPresentationAllFieldsRegular() {
    final PresentationStripe source = new PresentationStripe();

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

  private void assertEquivalent(final @NonNull DomainStripe dataStripe,
      final @NonNull PresentationStripe databaseStripe) {
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
