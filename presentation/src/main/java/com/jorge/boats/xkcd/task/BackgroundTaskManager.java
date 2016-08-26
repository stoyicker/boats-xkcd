package com.jorge.boats.xkcd.task;

import android.support.annotation.NonNull;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.PeriodicTask;
import com.google.android.gms.gcm.Task;

public abstract class BackgroundTaskManager {

  private BackgroundTaskManager() {
    throw new IllegalAccessError("No instances");
  }

  public static void initialize(final @NonNull GcmNetworkManager networkManager) {
    scheduleScrappingTask(networkManager);
  }

  private static void scheduleScrappingTask(final @NonNull GcmNetworkManager networkManager) {
    networkManager.schedule(buildUserRetentionTask());
  }

  private static Task buildUserRetentionTask() {
    final Class<? extends GcmTaskService> taskClass = TaskProvider.provideUserRetentionTask();
    final String taskTag = taskClass.getName();

    return new PeriodicTask.Builder()
        .setFlex(30 * 60)
        .setPeriod(45 * 60)
        .setRequiredNetwork(Task.NETWORK_STATE_ANY)
        .setRequiresCharging(false)
        .setService(taskClass)
        .setTag(taskTag)
        .setUpdateCurrent(true)
        .build();
  }
}
