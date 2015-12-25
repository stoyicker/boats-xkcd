package com.jorge.boats.data.entity;

import java.util.Random;
import org.apache.commons.lang3.RandomStringUtils;

abstract class ValueGenerator {

  enum Value {
    NULL, REGULAR
  }

  static String generateString(final Value type) {
    switch (type) {
      case NULL:
        return null;
      case REGULAR:
        return RandomStringUtils.random(1 + Math.abs(new Random().nextInt(100)));
      default:
        throw new IllegalStateException("Unsupported value " + type.name());
    }
  }

  static long generateLong(final Value type) {
    switch (type) {
      case NULL:
        return 0;
      case REGULAR:
        return new Random().nextLong();
      default:
        throw new IllegalStateException("Unsupported value " + type.name());
    }
  }

  private ValueGenerator() {
    throw new IllegalAccessError("No instances.");
  }
}
