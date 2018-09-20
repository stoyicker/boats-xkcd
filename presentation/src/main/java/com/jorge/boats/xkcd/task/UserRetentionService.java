package com.jorge.boats.xkcd.task;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;
import android.support.v4.app.NotificationCompat;

import com.jorge.boats.xkcd.R;
import com.jorge.boats.xkcd.data.P;
import com.jorge.boats.xkcd.view.stripe.StripeActivity;

public class UserRetentionService extends JobIntentService {
  private static final long SELF_SCHEDULE_DELAY_MILLIS = 1000 * 60 * 60 * 24 * 1; // 1 day
  private static final long APP_IGNORED_LIMIT_MILLISECONDS = 1000 * 60 * 60 * 24 * 8; // 8 days

  @Override
  protected void onHandleWork(@NonNull Intent intent) {
    try {
      if (System.currentTimeMillis() - Long.parseLong(P.lastOpenedEpoch.get())
              > APP_IGNORED_LIMIT_MILLISECONDS) {
        showReengageNotification();
      }
    } catch (final @NonNull NumberFormatException ignored) {
      //The preference has not been written yet, so just move on to reschedule
    }
    reschedule();
  }

  private void reschedule() {
    final AlarmManager alarmManager = ((AlarmManager) getApplicationContext().getSystemService(
            Context.ALARM_SERVICE));
    if (alarmManager != null) {
      alarmManager.set(
              AlarmManager.RTC_WAKEUP,
              System.currentTimeMillis() + SELF_SCHEDULE_DELAY_MILLIS,
              PendingIntent.getBroadcast(
                      getApplicationContext(),
                      0,
                      OnBootBroadcastReceiver.callingIntent(getApplicationContext()),
                      PendingIntent.FLAG_ONE_SHOT));
    }
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    reschedule();
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
        .setContentIntent(
            PendingIntent.getActivity(appContext, -1, intent, PendingIntent.FLAG_ONE_SHOT));

    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
      //noinspection InlinedApi -- To not to pop lint
      notificationBuilder.setCategory(Notification.CATEGORY_RECOMMENDATION);
    }

    ((NotificationManager) appContext.getSystemService(NOTIFICATION_SERVICE)).notify(
        R.id.notification_user_retention, notificationBuilder.build());
  }
}
