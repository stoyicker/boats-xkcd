package com.jorge.boats.xkcd.data.di.module;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jorge.boats.xkcd.data.net.XkcdClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataModule {

  private final Context mContext;

  public DataModule(final @NonNull Context context) {
    mContext = context;
  }

  @Provides
  Context provideContext() {
    return mContext;
  }

  @Provides
  @Singleton
  XkcdClient provideXkcdClient(final @NonNull Context context) {
    return new XkcdClient(context);
  }
}
