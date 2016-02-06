package com.jorge.boats.xkcd.util;

import com.jorge.boats.xkcd.BuildConfig;

public abstract class ProductUtil {

  private ProductUtil() {
    throw new IllegalAccessError("No instances.");
  }

  public static boolean hasProPower() {
    return BuildConfig.POWER_PRO;
  }
}
