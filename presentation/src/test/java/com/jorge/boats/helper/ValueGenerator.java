package com.jorge.boats.helper;

import com.jorge.boats.data.db.DatabaseStripe;
import com.jorge.boats.data.model.DataStripe;
import com.jorge.boats.domain.entity.DomainStripe;
import java.util.Random;
import org.apache.commons.lang3.RandomStringUtils;

public abstract class ValueGenerator {

  public enum Value {
    NULL, REGULAR
  }

  public static String generateString(final Value type) {
    switch (type) {
      case NULL:
        return null;
      case REGULAR:
        return RandomStringUtils.random(1 + Math.abs(new Random().nextInt(100)));
      default:
        throw new IllegalStateException("Unsupported value " + type.name());
    }
  }

  public static long generateLong(final Value type) {
    switch (type) {
      case NULL:
        return 0;
      case REGULAR:
        return 1 + Math.abs(new Random().nextLong());
      default:
        throw new IllegalStateException("Unsupported value " + type.name());
    }
  }

  public static DatabaseStripe generateRandomDatabaseStripe() {
    final DatabaseStripe ret = new DatabaseStripe();

    ret.setAlt(generateString(ValueGenerator.Value.REGULAR));
    ret.setDay(generateString(ValueGenerator.Value.REGULAR));
    ret.setImg(generateString(ValueGenerator.Value.REGULAR));
    ret.setLink(generateString(ValueGenerator.Value.REGULAR));
    ret.setMonth(generateString(ValueGenerator.Value.REGULAR));
    ret.setYear(generateString(ValueGenerator.Value.REGULAR));
    ret.setNews(generateString(ValueGenerator.Value.REGULAR));
    ret.setNum(generateLong(ValueGenerator.Value.REGULAR));
    ret.setTitle(generateString(ValueGenerator.Value.REGULAR));
    ret.setSafe_title(generateString(ValueGenerator.Value.REGULAR));
    ret.setTranscript(generateString(ValueGenerator.Value.REGULAR));

    return ret;
  }

  public static DataStripe generateRandomDataStripe() {
    final DataStripe ret = new DataStripe();

    ret.setAlt(generateString(ValueGenerator.Value.REGULAR));
    ret.setDay(generateString(ValueGenerator.Value.REGULAR));
    ret.setImg(generateString(ValueGenerator.Value.REGULAR));
    ret.setLink(generateString(ValueGenerator.Value.REGULAR));
    ret.setMonth(generateString(ValueGenerator.Value.REGULAR));
    ret.setYear(generateString(ValueGenerator.Value.REGULAR));
    ret.setNews(generateString(ValueGenerator.Value.REGULAR));
    ret.setNum(generateLong(ValueGenerator.Value.REGULAR));
    ret.setTitle(generateString(ValueGenerator.Value.REGULAR));
    ret.setSafe_title(generateString(ValueGenerator.Value.REGULAR));
    ret.setTranscript(generateString(ValueGenerator.Value.REGULAR));

    return ret;
  }

  public static DomainStripe generateRandomDomainStripe() {
    final DomainStripe ret = new DomainStripe();

    ret.setAlt(generateString(ValueGenerator.Value.REGULAR));
    ret.setDay(generateString(ValueGenerator.Value.REGULAR));
    ret.setImg(generateString(ValueGenerator.Value.REGULAR));
    ret.setLink(generateString(ValueGenerator.Value.REGULAR));
    ret.setMonth(generateString(ValueGenerator.Value.REGULAR));
    ret.setYear(generateString(ValueGenerator.Value.REGULAR));
    ret.setNews(generateString(ValueGenerator.Value.REGULAR));
    ret.setNum(generateLong(ValueGenerator.Value.REGULAR));
    ret.setTitle(generateString(ValueGenerator.Value.REGULAR));
    ret.setSafe_title(generateString(ValueGenerator.Value.REGULAR));
    ret.setTranscript(generateString(ValueGenerator.Value.REGULAR));

    return ret;
  }

  private ValueGenerator() {
    throw new IllegalAccessError("No instances.");
  }
}
