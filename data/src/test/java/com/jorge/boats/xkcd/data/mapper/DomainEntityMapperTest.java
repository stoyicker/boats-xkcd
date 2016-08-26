package com.jorge.boats.xkcd.data.mapper;

import android.support.annotation.NonNull;

import com.jorge.boats.xkcd.data.model.DataStripe;
import com.jorge.boats.xkcd.domain.entity.DomainStripe;

import org.junit.Before;
import org.junit.Test;

import static com.jorge.boats.xkcd.data.helper.ValueGenerator.Value;
import static com.jorge.boats.xkcd.data.helper.ValueGenerator.generateLong;
import static com.jorge.boats.xkcd.data.helper.ValueGenerator.generateString;
import static org.assertj.core.api.Assertions.assertThat;

public class DomainEntityMapperTest {

  private DomainEntityMapper mSut;

  @Before
  public void setUp() {
    mSut = new DomainEntityMapper();
  }

  @Test
  public void testTransformNull() {
    assertThat(mSut.transform(null)).isNull();
  }

  @Test
  public void testTransformDataAllFieldsNull() {
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

  @Test
  public void testTransformDataAllFieldsRegular() {
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

  private void assertEquivalent(final @NonNull DataStripe source,
      final @NonNull DomainStripe product) {
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
