package com.jorge.boats.xkcd.task;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

public abstract class BackgroundTaskManager {

  private BackgroundTaskManager() {
    throw new IllegalAccessError("No instances");
  }

  public static void initialize(final @NonNull Context context) {
    scheduleUserRetentionTask(context);
  }

  private static void scheduleUserRetentionTask(final Context context) {
    UserRetentionService.enqueueWork(
            context, UserRetentionService.class, 1000, new Intent());
  }
}
