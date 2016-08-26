package com.jorge.boats.xkcd.task;

import com.google.android.gms.gcm.GcmTaskService;

public abstract class TaskProvider {

  private TaskProvider() {
    throw new IllegalAccessError("No instances");
  }

  static Class<? extends GcmTaskService> provideUserRetentionTask() {
    return UserRetentionGcmTaskService.class;
  }
}
