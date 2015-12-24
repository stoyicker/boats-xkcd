package com.jorge.boats.data.entity;

import com.jorge.boats.data.ApplicationTestCase;
import com.jorge.boats.data.model.DataStripe;
import com.jorge.boats.domain.entity.DomainStripe;
import java.util.Random;
import org.apache.commons.lang3.RandomStringUtils;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EntityMapperTest extends ApplicationTestCase {

  private enum Value {
    NULL, REGULAR
  }

  private EntityMapper mSut;

  @Before public void setUp() {
    mSut = new EntityMapper();
  }

  private static String generateString(final Value type) {
    switch (type) {
      case NULL:
        return null;
      case REGULAR:
        return RandomStringUtils.random(1 + new Random().nextInt());
      default:
        throw new IllegalStateException("Unsupported value " + type.name());
    }
  }

  private static int generateInt(final Value type) {
    switch (type) {
      case NULL:
        return 0;
      case REGULAR:
        return 1 + new Random().nextInt();
      default:
        throw new IllegalStateException("Unsupported value " + type.name());
    }
  }

  @Test public void testTransformNull() {
    assertThat(mSut.transform(null)).isNull();
  }

  @Test public void testAllFieldsNull() {
    final DataStripe source = new DataStripe();

    source.setAlt(generateString(Value.NULL));
    source.setDay(generateString(Value.NULL));
    source.setImg(generateString(Value.NULL));
    source.setLink(generateString(Value.NULL));
    source.setMonth(generateString(Value.NULL));
    source.setYear(generateString(Value.NULL));
    source.setNews(generateString(Value.NULL));
    source.setNum(generateInt(Value.NULL));
    source.setTitle(generateString(Value.NULL));
    source.setSafe_title(generateString(Value.NULL));
    source.setTranscript(generateString(Value.NULL));

    assertEquivalent(source, mSut.transform(source));
  }

  @Test public void testAllFieldsRegular() {
    final DataStripe source = new DataStripe();

    source.setAlt(generateString(Value.REGULAR));
    source.setDay(generateString(Value.REGULAR));
    source.setImg(generateString(Value.REGULAR));
    source.setLink(generateString(Value.REGULAR));
    source.setMonth(generateString(Value.REGULAR));
    source.setYear(generateString(Value.REGULAR));
    source.setNews(generateString(Value.REGULAR));
    source.setNum(generateInt(Value.REGULAR));
    source.setTitle(generateString(Value.REGULAR));
    source.setSafe_title(generateString(Value.REGULAR));
    source.setTranscript(generateString(Value.REGULAR));

    assertEquivalent(source, mSut.transform(source));
  }

  private void assertEquivalent(final @NotNull DataStripe source,
      final @NotNull DomainStripe product) {
    assertThat(source.getAlt()).isEqualTo(product.getAlt());
    assertThat(source.getDay()).isEqualTo(product.getDay());
    assertThat(source.getImg()).isEqualTo(product.getImg());
    assertThat(source.getLink()).isEqualTo(product.getLink());
    assertThat(source.getMonth()).isEqualTo(product.getMonth());
    assertThat(source.getYear()).isEqualTo(product.getYear());
    assertThat(source.getNews()).isEqualTo(product.getNews());
    assertThat(source.getNum()).isEqualTo(product.getNum());
    assertThat(source.getTitle()).isEqualTo(product.getTitle());
    assertThat(source.getSafe_title()).isEqualTo(product.getSafe_title());
    assertThat(source.getTranscript()).isEqualTo(product.getTranscript());
  }
}
