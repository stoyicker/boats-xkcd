package com.jorge.boats.xkcd.log;

public abstract class ApplicationLogger {

  private ApplicationLogger() {
    throw new IllegalAccessError("No instances.");
  }

  public static void d(final String message, final Object... args) {
    //No-op
  }

  public static void e(final Throwable throwable, final String message, final Object... args) {
    //No-op
  }
}