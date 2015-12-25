package com.jorge.boats.data.di.module;

import android.content.Context;
import android.support.annotation.NonNull;
import com.jorge.boats.data.net.client.XkcdClient;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module public class DataModule {

  private final Context mContext;

  public DataModule(final @NonNull Context context) {
    mContext = context;
  }

  @Provides Context provideContext() {
    return mContext;
  }

  @Provides @Singleton XkcdClient provideXkcdClient(final @NonNull Context context) {
    return new XkcdClient(context);
  }
}
