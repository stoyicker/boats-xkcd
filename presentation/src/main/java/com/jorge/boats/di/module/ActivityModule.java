package com.jorge.boats.di.module;

import android.app.Activity;
import dagger.Module;
import dagger.Provides;
import com.jorge.boats.di.PerActivity;

@Module public class ActivityModule {
  private final Activity mActivity;

  public ActivityModule(final Activity activity) {
    this.mActivity = activity;
  }

  @Provides @PerActivity Activity activity() {
    return this.mActivity;
  }
}