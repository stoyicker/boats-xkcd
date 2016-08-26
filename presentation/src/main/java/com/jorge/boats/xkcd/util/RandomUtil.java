package com.jorge.boats.xkcd.util;

import android.os.Build;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public abstract class RandomUtil {

  private static final Random GENERATOR = new Random();

  private RandomUtil() {
    throw new IllegalAccessError("No instances.");
  }

  public static long nextLong(final int min, final int max) {
    final int innerMin = Math.max(1, min), innerMax = Math.max(2, max);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      return ThreadLocalRandom.current().nextLong(innerMin, innerMax + 1);
    } else {
      return GENERATOR.nextInt(innerMax + 1 - innerMin) + innerMin;
    }
  }
}