package com.jorge.boats.data;

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

  private ValueGenerator() {
    throw new IllegalAccessError("No instances.");
  }
}
