package com.jorge.boats.xkcd.task;

import android.support.annotation.NonNull;

import com.google.android.gms.gcm.GcmNetworkManager;

public abstract class BackgroundTaskManager {

    private BackgroundTaskManager() {
        throw new IllegalAccessError("No instances");
    }

    public static void initialize(final @NonNull GcmNetworkManager networkManager) {
        scheduleScrappingTask(networkManager);
    }

    private static void scheduleScrappingTask(final @NonNull GcmNetworkManager networkManager) {
        networkManager.schedule(UserRetentionGcmTaskService.buildPeriodicScrapTask());
    }
}
