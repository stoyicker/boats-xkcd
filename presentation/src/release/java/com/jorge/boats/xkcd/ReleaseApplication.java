package com.jorge.boats.xkcd;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

public final class ReleaseApplication extends MainApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        this.initializeCreepers();
    }

    private void initializeCreepers() {
        Fabric.with(this, new Crashlytics());
    }
}
