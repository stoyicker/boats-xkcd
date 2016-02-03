package com.jorge.boats.xkcd;

import android.app.Application;
import com.jorge.boats.xkcd.data.DataManager;
import com.jorge.boats.xkcd.di.component.ApplicationComponent;
import com.jorge.boats.xkcd.di.component.DaggerApplicationComponent;
import com.jorge.boats.xkcd.di.module.ApplicationModule;

public class CustomApplication extends Application {

  private ApplicationComponent mApplicationComponent;

  @Override public void onCreate() {
    super.onCreate();

    this.initializeInjector();
    this.initializeData();
  }

  private void initializeInjector() {
    this.mApplicationComponent =
        DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
  }

  private void initializeData() {
    DataManager.initialize(this);
  }

  public ApplicationComponent getApplicationComponent() {
    return this.mApplicationComponent;
  }
}