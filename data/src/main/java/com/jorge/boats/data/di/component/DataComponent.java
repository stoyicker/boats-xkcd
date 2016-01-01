package com.jorge.boats.data.di.component;

import com.jorge.boats.data.di.module.DataModule;
import dagger.Component;
import javax.inject.Singleton;

@Singleton @Component(modules = DataModule.class) public interface DataComponent {
}
