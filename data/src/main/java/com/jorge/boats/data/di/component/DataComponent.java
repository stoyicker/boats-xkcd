package com.jorge.boats.data.di.component;

import android.content.Context;
import com.jorge.boats.data.di.module.DataModule;
import com.jorge.boats.data.net.XkcdClient;
import dagger.Component;
import javax.inject.Singleton;

@Singleton @Component(modules = DataModule.class) public interface DataComponent {

  Context context();

  XkcdClient xkcdClient();
}
