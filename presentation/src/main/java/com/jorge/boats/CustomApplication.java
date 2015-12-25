package com.jorge.boats;

import android.app.Application;
import com.jorge.boats.data.DataInitializer;
import com.jorge.boats.di.component.ApplicationComponent;
import com.jorge.boats.di.component.DaggerApplicationComponent;
import com.jorge.boats.di.module.ApplicationModule;
import com.raizlabs.android.dbflow.config.FlowManager;

public class CustomApplication extends Application {

  private ApplicationComponent mApplicationComponent;

  @Override public void onCreate() {
    super.onCreate();
    this.initializeInjector();
    this.initializeSharedPreferences();
    this.initializeData();
  }

  private void initializeInjector() {
    this.mApplicationComponent =
        DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
  }

  private void initializeSharedPreferences() {
    P.init(this);
  }

  private void initializeData() {
    FlowManager.init(this);
    DataInitializer.initialize(this);
  }

  public ApplicationComponent getApplicationComponent() {
    return this.mApplicationComponent;
  }
}