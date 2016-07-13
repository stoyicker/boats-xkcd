package com.jorge.boats.xkcd.task;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;

import com.jorge.boats.xkcd.R;
import com.jorge.boats.xkcd.data.P;
import com.jorge.boats.xkcd.view.stripe.StripeActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

public final class UserRetentionGcmTaskService extends GcmTaskService {

    private static final long APP_IGNORED_LIMIT_MILLISECONDS = 1000 * 60 * 60 * 24 * 4;

    @Override
    public int onRunTask(final @Nullable TaskParams taskParams) {
        try {
            if (System.currentTimeMillis() - Long.parseLong(P.lastOpenedEpoch.get()) > APP_IGNORED_LIMIT_MILLISECONDS) {
                showReengageNotification();
            }
            return GcmNetworkManager.RESULT_SUCCESS;
        } catch (final @NonNull NumberFormatException e) {
            //The preference has not been written yet, so just reschedule and move on
            return GcmNetworkManager.RESULT_RESCHEDULE;
        }
    }

    private void showReengageNotification() {
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
            //noinspection InlinedApi -- To not to pop lint
            notificationBuilder.setCategory(Notification.CATEGORY_RECOMMENDATION);
        }

        ((NotificationManager) appContext.getSystemService(NOTIFICATION_SERVICE)).notify(R.id.notification_user_retention, notificationBuilder.build());
    }
}
