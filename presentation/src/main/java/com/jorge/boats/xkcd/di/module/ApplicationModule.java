package com.jorge.boats.xkcd.di.module;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.jorge.boats.xkcd.UIThread;
import com.jorge.boats.xkcd.data.XkcdStoreImpl;
import com.jorge.boats.xkcd.data.executor.JobExecutor;
import com.jorge.boats.xkcd.domain.executor.PostExecutionThread;
import com.jorge.boats.xkcd.domain.executor.ThreadExecutor;
import com.jorge.boats.xkcd.domain.repository.XkcdStore;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

  private final Application mApplication;

  public ApplicationModule(final Application application) {
    this.mApplication = application;
  }

  @Provides
  @Singleton
  Context provideApplicationContext() {
    return this.mApplication;
  }

  @Provides
  @Singleton
  ThreadExecutor provideThreadExecutor(
      final @NonNull JobExecutor jobExecutor) {
    return jobExecutor;
  }

  @Provides
  @Singleton
  PostExecutionThread providePostExecutionThread(
      final @NonNull UIThread uiThread) {
    return uiThread;
  }

  @Provides
  @Singleton
  XkcdStore provideXkcdStore(final @NonNull XkcdStoreImpl xkcdStoreImpl) {
    return xkcdStoreImpl;
  }
}
