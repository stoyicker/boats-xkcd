package com.jorge.boats.di.component;

import android.content.Context;
import com.jorge.boats.di.module.ApplicationModule;
import com.jorge.boats.domain.executor.PostExecutionThread;
import com.jorge.boats.domain.executor.ThreadExecutor;
import com.jorge.boats.navigation.Navigator;
import com.jorge.boats.view.activity.BaseActivity;
import dagger.Component;
import javax.inject.Singleton;

@Singleton @Component(modules = ApplicationModule.class) public interface ApplicationComponent
    extends AbstractComponent {

  void inject(final BaseActivity baseActivity);

  Navigator navigator();

  Context context();

  ThreadExecutor threadExecutor();

  PostExecutionThread postExecutionThread();
}
