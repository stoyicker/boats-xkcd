package com.jorge.boats.xkcd.util;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.util.TypedValue;

public abstract class ResourceUtil {

  private ResourceUtil() {
    throw new IllegalAccessError("No instances.");
  }

  public static int getAttrColor(final @NonNull Context context, final @AttrRes int resId) {
    final TypedValue typedValue = new TypedValue();
    final Resources.Theme theme = context.getTheme();

    theme.resolveAttribute(resId, typedValue, true);

    return typedValue.data;
  }
}
