package com.jorge.boats.xkcd.util;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;

import static android.os.Build.VERSION;
import static android.os.Build.VERSION_CODES;

public abstract class ActivityUtil {

  private ActivityUtil() {
    throw new IllegalAccessError("No instances");
  }

  public static void restart(final @NonNull Activity activity) {
    if (VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB) {
      activity.recreate();
    } else {
      final Intent intent = activity.getIntent();
      activity.finish();
      activity.startActivity(intent);
    }
  }
}
