package com.jorge.boats.xkcd.util;

import android.app.Activity;
import android.support.annotation.NonNull;

public abstract class ActivityUtil {

  private ActivityUtil() {
    throw new IllegalAccessError("No instances");
  }

  public static void restart(final @NonNull Activity activity) {
    activity.recreate();
  }
}
