package com.jorge.boats.xkcd.util;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import com.jorge.boats.xkcd.BuildConfig;
import com.jorge.boats.xkcd.log.ApplicationLogger;
import com.jorge.boats.xkcd.view.stripe.StripeActivity;

public abstract class ProductUtil {

  private ProductUtil() {
    throw new IllegalAccessError("No instances.");
  }

  public static boolean hasProPower() {
    return BuildConfig.POWER_PRO;
  }

  public static void showProAppPlayStoreEntry(final @NonNull Context context) {
    final String proAppPackageName = getProPackageName(context);

    try {
      context.startActivity(
          new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + proAppPackageName)));
    } catch (final @NonNull ActivityNotFoundException e) {
      context.startActivity(new Intent(Intent.ACTION_VIEW,
          Uri.parse("http://play.google.com/store/apps/details?id=" + proAppPackageName)));
    }
  }

  private static String getProPackageName(final @NonNull Context context) {
    final String base = context.getPackageName();

    if (!base.contains(".pro")) {
      return base + ".pro";
    } else {
      return base;
    }
  }

  /**
   * @return <value>false</value> if a new app is started. <value>false</value> otherwise.
   */
  public static boolean runMaxPowerIfAvailable(final @NonNull Activity activity,
      final long stripeNum) {
    if (hasProPower()) return false;

    final PackageManager pm = activity.getPackageManager();

    try {
      final Intent launchIntent = pm.getLaunchIntentForPackage(getProPackageName(activity));
      launchIntent.putExtra(StripeActivity.INTENT_EXTRA_PARAM_STRIPE_NUM, stripeNum);
      launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
          .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
          .addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
      activity.startActivity(launchIntent);
      activity.finish();
    } catch (final @NonNull Exception e) {
      ApplicationLogger.e(e, e.getMessage());
    }

    return true;
  }
}
