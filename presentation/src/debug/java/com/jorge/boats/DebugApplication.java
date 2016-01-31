package com.jorge.boats;

import com.squareup.leakcanary.LeakCanary;
import timber.log.Timber;

public final class DebugApplication extends CustomApplication {

  @Override public void onCreate() {
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
