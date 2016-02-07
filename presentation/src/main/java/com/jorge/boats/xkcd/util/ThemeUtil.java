package com.jorge.boats.xkcd.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import com.jorge.boats.xkcd.R;
import com.jorge.boats.xkcd.data.P;
import java.util.Locale;

public abstract class ThemeUtil {

  private ThemeUtil() {
    throw new IllegalAccessError("No instances.");
  }

  @StyleRes public static int getAppTheme(final @NonNull Context context) {
    final String themeName, theme0Name = context.getString(R.string.theme_0_name), theme1Name =
        context.getString(R.string.theme_1_name);

    if ((themeName = P.themeName.get()).contentEquals(theme0Name)) {
      return R.style.App;
    } else if (themeName.contentEquals(theme1Name)) {
      return R.style.AppNegative;
    } else {
      throw new IllegalStateException(
          String.format(Locale.ENGLISH, "Unrecognized theme name %s", themeName));
    }
  }

  @StyleRes public static int getSettingsTheme(final @NonNull Context context) {
    final String themeName, theme0Name = context.getString(R.string.theme_0_name), theme1Name =
        context.getString(R.string.theme_1_name);

    if ((themeName = P.themeName.get()).contentEquals(theme0Name)) {
      return R.style.SettingsTheme;
    } else if (themeName.contentEquals(theme1Name)) {
      return R.style.SettingsThemeNegative;
    } else {
      throw new IllegalStateException(
          String.format(Locale.ENGLISH, "Unrecognized theme name %s", themeName));
    }
  }
}
