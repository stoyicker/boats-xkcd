package com.jorge.boats.xkcd.task;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.PeriodicTask;
import com.google.android.gms.gcm.Task;
import com.google.android.gms.gcm.TaskParams;
import com.jorge.boats.xkcd.R;
import com.jorge.boats.xkcd.view.stripe.StripeActivity;

public class UserRetentionGcmTaskService extends GcmTaskService {

    private static final String TASK_TAG = UserRetentionGcmTaskService.class.getName();
    private static final long APP_IGNORED_LIMIT_MILLISECONDS = 1000 * 60 * 60 * 24 * 4;

    @Override
    public int onRunTask(final @Nullable TaskParams taskParams) {
//        if (System.currentTimeMillis() - Long.parseLong(P.lastOpenedEpoch.get()) > APP_IGNORED_LIMIT_MILLISECONDS) {
        showReengageNotification();
//        }
        return GcmNetworkManager.RESULT_SUCCESS;
    }

    private void showReengageNotification() {
        Log.d("JORGETEST", "Showing 17");
        final Context appContext;
        final Intent intent = new Intent(appContext = getApplicationContext(), StripeActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        final NotificationCompat.Builder
                notificationBuilder = new NotificationCompat.Builder(appContext)
                .setAutoCancel(true)
                .setContentTitle(getString(R.string.notification_title_user_retention))
                .setContentText(getString(R.string.notification_user_retention_content))
                .setShowWhen(false)
                .setDefaults(Notification.DEFAULT_ALL)
                .setLocalOnly(true)
                .setSmallIcon(R.drawable.ic_new_box)
                .setContentIntent(PendingIntent.getActivity(appContext, -1, intent, PendingIntent.FLAG_ONE_SHOT));


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.setCategory(Notification.CATEGORY_RECOMMENDATION);
        }

        ((NotificationManager) appContext.getSystemService(NOTIFICATION_SERVICE)).notify(R.id.notification_user_retention, notificationBuilder.build());
    }

    public static Task buildPeriodicScrapTask() {
        Log.d("JORGETEST", "Buidlging 17");
        return new PeriodicTask.Builder()
                .setFlex(1)
                .setPeriod(1)
//                .setFlex(30 * 60)
//                .setPeriod(45 * 60)
                .setRequiredNetwork(Task.NETWORK_STATE_ANY)
                .setRequiresCharging(false)
                .setService(UserRetentionGcmTaskService.class)
                .setTag(TASK_TAG)
                .setUpdateCurrent(true)
                .build();
    }
}
