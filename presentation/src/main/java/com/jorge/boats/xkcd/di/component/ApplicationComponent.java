package com.jorge.boats.xkcd.di.component;

import android.content.Context;

import com.jorge.boats.xkcd.di.module.ApplicationModule;
import com.jorge.boats.xkcd.domain.executor.PostExecutionThread;
import com.jorge.boats.xkcd.domain.executor.ThreadExecutor;
import com.jorge.boats.xkcd.domain.repository.XkcdStore;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent
    extends AbstractComponent {

  Context context();

  ThreadExecutor threadExecutor();

  PostExecutionThread postExecutionThread();

  XkcdStore xkcdStore();
}
