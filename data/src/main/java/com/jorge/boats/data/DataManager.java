package com.jorge.boats.data;

import android.app.Application;
import android.support.annotation.NonNull;
import com.jorge.boats.data.di.component.DaggerDataComponent;
import com.jorge.boats.data.di.module.DataModule;
import com.raizlabs.android.dbflow.config.FlowManager;

public abstract class DataManager {

  private DataManager() {
  }

  public static void initialize(final @NonNull Application application) {
    DaggerDataComponent.builder().dataModule(new DataModule(application)).build();
    FlowManager.init(application);
  }

  static void destroy() {
    FlowManager.destroy();
  }
}
