package com.jorge.boats;

import timber.log.Timber;

public final class DebugApplication extends CustomApplication {

  @Override public void onCreate() {
    super.onCreate();
    Timber.plant(new Timber.DebugTree());
  }
}
