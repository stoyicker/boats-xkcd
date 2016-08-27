package com.jorge.boats.xkcd;

import timber.log.Timber;

public final class DebugApplication extends MainApplication {

  @Override
  public void onCreate() {
    super.onCreate();

    this.initializeLoggers();
  }

  private void initializeLoggers() {
    Timber.plant(new Timber.DebugTree());
  }
}
