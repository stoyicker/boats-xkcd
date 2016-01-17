package com.jorge.boats.log;

import android.support.annotation.NonNull;

public class ApplicationLogger {

  private ApplicationLogger() {
    throw new IllegalAccessError("No instances.");
  }

  public static void d(@NonNull String message) {
    //Do nothing
  }

  public static void e(@NonNull Throwable throwable, @NonNull String message) {
    //Do nothing
  }
}
