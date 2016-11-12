package com.jorge.boats.xkcd;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;

import io.fabric.sdk.android.Fabric;

public final class ReleaseApplication extends MainApplication {

  @Override
  public void onCreate() {
    super.onCreate();

    this.initializeCreepers();
  }

  private void initializeCreepers() {
    Fabric.with(
            new Fabric.Builder(this)
                    .kits(new Crashlytics(), new Answers())
                    .build());
  }
}
