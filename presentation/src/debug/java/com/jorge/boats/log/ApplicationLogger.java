package com.jorge.boats.log;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.Locale;
import timber.log.Timber;

public abstract class ApplicationLogger {

  private ApplicationLogger() {
    throw new IllegalAccessError("No instances.");
  }

  public static void d(final @NonNull String message, final @Nullable Object... args) {
    Timber.d(String.format(Locale.ENGLISH, message, args));
  }

  public static void e(final @NonNull Throwable throwable, final @NonNull String message,
      final @Nullable Object... args) {
    Timber.e(throwable, String.format(Locale.ENGLISH, message, args));
  }
}
