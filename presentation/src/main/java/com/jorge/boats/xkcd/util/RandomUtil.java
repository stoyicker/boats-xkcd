package com.jorge.boats.xkcd.util;

import android.os.Build;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public abstract class RandomUtil {

  private static final Random GENERATOR = new Random();

  private RandomUtil() {
    throw new IllegalAccessError("No instances.");
  }

  public static long nextInt(final int min, final int max) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      return ThreadLocalRandom.current().nextLong(min, max + 1);
    } else {
      return GENERATOR.nextInt(max + 1 - min) + min;
    }
  }
}