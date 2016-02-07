package com.jorge.boats.xkcd.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import com.jorge.boats.xkcd.BuildConfig;
import java.util.Locale;

public abstract class ProductUtil {

  private ProductUtil() {
    throw new IllegalAccessError("No instances.");
  }

  public static boolean hasProPower() {
    return BuildConfig.POWER_PRO;
  }

  public static void showProAppPlayStoreEntry(final @NonNull Context context) {
    final String proAppPackageName =
        String.format(Locale.ENGLISH, context.getPackageName(), ".pro");

    try {
      context.startActivity(
          new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + proAppPackageName)));
    } catch (android.content.ActivityNotFoundException anfe) {
      context.startActivity(new Intent(Intent.ACTION_VIEW,
          Uri.parse("http://play.google.com/store/apps/details?id=" + proAppPackageName)));
    }
  }
}
