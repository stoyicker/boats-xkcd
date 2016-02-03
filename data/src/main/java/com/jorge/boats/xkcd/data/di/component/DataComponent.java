package com.jorge.boats.xkcd.data.di.component;

import com.jorge.boats.xkcd.data.di.module.DataModule;
import dagger.Component;
import javax.inject.Singleton;

@Singleton @Component(modules = DataModule.class) public interface DataComponent {
}
