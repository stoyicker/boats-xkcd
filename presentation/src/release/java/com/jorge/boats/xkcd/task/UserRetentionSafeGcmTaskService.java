package com.jorge.boats.xkcd.task;

import android.support.annotation.Nullable;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.TaskParams;

public class UserRetentionSafeGcmTaskService extends UserRetentionGcmTaskService {

    @Override
    public int onRunTask(@Nullable TaskParams taskParams) {
        try {
            return super.onRunTask(taskParams);
        } catch (final @Nullable Exception componentNotRegistered) {
            Log.w(UserRetentionGcmTaskService.class.getName(), componentNotRegistered.getMessage()); // We don't want ApplicationLogger here
            Crashlytics.logException(componentNotRegistered);
            return GcmNetworkManager.RESULT_RESCHEDULE;
        }
    }
}
