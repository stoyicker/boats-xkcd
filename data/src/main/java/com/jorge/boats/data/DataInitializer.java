package com.jorge.boats.data;

import android.content.Context;
import android.support.annotation.NonNull;
import com.jorge.boats.data.di.component.DaggerDataComponent;
import com.jorge.boats.data.di.module.DataModule;

public abstract class DataInitializer {

  private DataInitializer() {
  }

  public static void initialize(final @NonNull Context context) {
    DaggerDataComponent.builder().dataModule(new DataModule(context)).build();
  }
}
