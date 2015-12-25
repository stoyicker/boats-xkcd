package com.jorge.boats.di.module;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import com.jorge.boats.UIThread;
import com.jorge.boats.data.executor.JobExecutor;
import com.jorge.boats.data.net.XkcdRepositoryImpl;
import com.jorge.boats.domain.executor.PostExecutionThread;
import com.jorge.boats.domain.executor.ThreadExecutor;
import com.jorge.boats.domain.repository.XkcdRepository;
import com.jorge.boats.navigation.Navigator;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module public class ApplicationModule {
  private final Application mApplication;

  public ApplicationModule(final Application application) {
    this.mApplication = application;
  }

  @Provides @Singleton Context provideApplicationContext() {
    return this.mApplication;
  }

  @Provides @Singleton Navigator provideNavigator() {
    return new Navigator();
  }

  @Provides @Singleton ThreadExecutor provideThreadExecutor(
      final @NonNull JobExecutor jobExecutor) {
    return jobExecutor;
  }

  @Provides @Singleton PostExecutionThread providePostExecutionThread(
      final @NonNull UIThread uiThread) {
    return uiThread;
  }

  @Provides @Singleton XkcdRepository provideXkcdRepository(
      final @NonNull XkcdRepositoryImpl xkcdRepositoryImpl) {
    return xkcdRepositoryImpl;
  }
}
