package com.jorge.boats.xkcd.task;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.TaskParams;

import com.crashlytics.android.Crashlytics;

import android.support.annotation.Nullable;

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
