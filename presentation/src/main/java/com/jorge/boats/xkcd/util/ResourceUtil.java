package com.jorge.boats.xkcd.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;

public abstract class ResourceUtil {

  private ResourceUtil() {
    throw new IllegalAccessError("No instances.");
  }

  public static int getColor(final @NonNull Context context, final @ColorRes int resId,
      final @Nullable Resources.Theme theme) throws Resources.NotFoundException {
    final int ret;

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      ret = context.getResources().getColor(resId, theme);
    } else {
      ret = ContextCompat.getColor(context, resId);
    }

    return ret;
  }

  public static int getAttrColor(final @NonNull Context context, final @AttrRes int resId) {
    final TypedValue typedValue = new TypedValue();
    final Resources.Theme theme = context.getTheme();

    theme.resolveAttribute(resId, typedValue, true);

    return typedValue.data;
  }

  @Nullable
  public static Drawable getDrawable(final @NonNull Context context, final @DrawableRes int resId,
      final @Nullable Resources.Theme theme) throws Resources.NotFoundException {
    final Drawable ret;

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      ret = context.getResources().getDrawable(resId, theme);
    } else {
      ret = ContextCompat.getDrawable(context, resId);
    }

    return ret;
  }
}
