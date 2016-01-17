package com.jorge.boats.di.component;

import android.content.Context;
import com.jorge.boats.di.module.ApplicationModule;
import com.jorge.boats.domain.executor.PostExecutionThread;
import com.jorge.boats.domain.executor.ThreadExecutor;
import com.jorge.boats.domain.repository.XkcdStore;
import dagger.Component;
import javax.inject.Singleton;

@Singleton @Component(modules = ApplicationModule.class) public interface ApplicationComponent
    extends AbstractComponent {

  Context context();

  ThreadExecutor threadExecutor();

  PostExecutionThread postExecutionThread();

  XkcdStore xkcdStore();
}
