package com.jorge.boats.xkcd.task;

import android.support.annotation.Nullable;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.TaskParams;

public class UserRetentionSafeGcmTaskService extends UserRetentionGcmTaskService {

  @Override
  public int onRunTask(@Nullable TaskParams taskParams) {
    try {
      return super.onRunTask(taskParams);
    } catch (final @Nullable Exception componentNotRegistered) {
      Crashlytics.logException(componentNotRegistered);
      return GcmNetworkManager.RESULT_RESCHEDULE;
    }
  }
}
