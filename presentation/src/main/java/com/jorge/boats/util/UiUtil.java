package com.jorge.boats.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Build;
import com.jorge.boats.R;

public abstract class UiUtil {

  private UiUtil() {
    throw new IllegalAccessError("No instances.");
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public static void setTaskDescription(final Activity activity) {
    final Context context = activity.getApplicationContext();

    activity.setTaskDescription(
        new ActivityManager.TaskDescription(context.getResources().getString(R.string.app_name),
            BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher),
            ResourceUtil.getAttrColor(context, R.attr.colorAccent)));
  }
}
