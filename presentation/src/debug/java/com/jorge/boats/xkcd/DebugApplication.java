package com.jorge.boats.xkcd;

import com.squareup.leakcanary.LeakCanary;

import timber.log.Timber;

public final class DebugApplication extends MainApplication {

  @Override
  public void onCreate() {
    super.onCreate();

    this.initializeInspectors();
    this.initializeLoggers();
  }

  private void initializeInspectors() {
    LeakCanary.install(this);
  }

  private void initializeLoggers() {
    Timber.plant(new Timber.DebugTree());
  }
}
