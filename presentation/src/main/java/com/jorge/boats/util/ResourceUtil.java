package com.jorge.boats.util;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public abstract class ResourceUtil {

  private ResourceUtil() {
    throw new IllegalAccessError("No instances.");
  }

  public static int getColor(final @NonNull Resources resources, final @ColorRes int resId,
      final @Nullable Resources.Theme theme) throws Resources.NotFoundException {
    final int ret;

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      ret = resources.getColor(resId, theme);
    } else {
      //noinspection deprecation - Handled
      ret = resources.getColor(resId);
    }

    return ret;
  }

  @NonNull public static Drawable getDrawable(final @NonNull Resources resources,
      final @DrawableRes int resId, final @Nullable Resources.Theme theme)
      throws Resources.NotFoundException {
    final Drawable ret;

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      ret = resources.getDrawable(resId, theme);
    } else {
      //noinspection deprecation - Handled
      ret = resources.getDrawable(resId);
    }

    //noinspection ConstantConditions - Wrong
    return ret;
  }
}
