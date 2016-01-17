package com.jorge.boats.log;

import android.support.annotation.NonNull;
import timber.log.Timber;

public class ApplicationLogger {

  private ApplicationLogger() {
    throw new IllegalAccessError("No instances.");
  }

  public static void d(@NonNull String message) {
    Timber.d(message);
  }

  public static void e(@NonNull Throwable throwable, @NonNull String message) {
    Timber.e(throwable, message);
  }
}
