package com.jorge.boats.xkcd.data.di.component;

import com.jorge.boats.xkcd.data.di.module.DataModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = DataModule.class)
public interface DataComponent {
}
