package com.jorge.boats.xkcd.data;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import com.jorge.boats.xkcd.data.di.component.DaggerDataComponent;
import com.jorge.boats.xkcd.data.di.module.DataModule;
import com.raizlabs.android.dbflow.config.FlowManager;

public abstract class DataManager {

  private DataManager() {
  }

  public static void initialize(final @NonNull Application application) {
    DaggerDataComponent.builder().dataModule(new DataModule(application)).build();
    FlowManager.init(application);
    initializeSharedPreferences(application.getApplicationContext());
  }

  private static void initializeSharedPreferences(final @NonNull Context applicationContext) {
    P.init(applicationContext);
  }

  static void destroy() {
    FlowManager.destroy();
  }
}
