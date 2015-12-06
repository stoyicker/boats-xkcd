package com.jorge.boats.di.component;

import android.content.Context;
import com.jorge.boats.di.module.ApplicationModule;
import com.jorge.boats.domain.executor.PostExecutionThread;
import com.jorge.boats.domain.executor.ThreadExecutor;
import com.jorge.boats.view.activity.BaseActivity;
import dagger.Component;
import javax.inject.Singleton;

@Singleton @Component(modules = ApplicationModule.class) public interface ApplicationComponent {
  void inject(final BaseActivity baseActivity);

  Context context();

  ThreadExecutor threadExecutor();

  PostExecutionThread postExecutionThread();
}
