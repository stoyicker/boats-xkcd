package com.jorge.boats.xkcd;

import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;

import io.fabric.sdk.android.Fabric;

public final class ReleaseApplication extends MainApplication {

  @Override
  public void onCreate() {
    super.onCreate();

    this.overwriteExceptionHandler();
    this.initializeCreepers();
  }

  private void initializeCreepers() {
    Fabric.with(
            new Fabric.Builder(this)
                    .kits(new Crashlytics(), new Answers())
                    .build());
  }

  private void overwriteExceptionHandler() {
    final Thread.UncaughtExceptionHandler uncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();

    Thread.currentThread().setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
      @Override
      public void uncaughtException(Thread thread, Throwable throwable) {
        if (throwable.getClass().getCanonicalName().contains("xposed")) {
            Crashlytics.logException(throwable);
        } else {
            uncaughtExceptionHandler.uncaughtException(thread, throwable);
        }
      }
    });
  }
}
