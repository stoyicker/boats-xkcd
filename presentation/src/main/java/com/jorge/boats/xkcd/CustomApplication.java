package com.jorge.boats.xkcd;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.jorge.boats.xkcd.BuildConfig;
import com.jorge.boats.xkcd.data.DataManager;
import com.jorge.boats.xkcd.di.component.ApplicationComponent;
import com.jorge.boats.xkcd.di.component.DaggerApplicationComponent;
import com.jorge.boats.xkcd.di.module.ApplicationModule;

import io.fabric.sdk.android.Fabric;

public class CustomApplication extends Application {

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        this.initializeCreepers();
        this.initializeInjector();
        this.initializeData();
    }

    private void initializeCreepers() {
        if (!BuildConfig.DEBUG) {
            Fabric.with(this, new Crashlytics());
        }
    }

    private void initializeInjector() {
        this.mApplicationComponent =
                DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
    }

    private void initializeData() {
        DataManager.initialize(this);
    }

    public ApplicationComponent getApplicationComponent() {
        return this.mApplicationComponent;
    }
}