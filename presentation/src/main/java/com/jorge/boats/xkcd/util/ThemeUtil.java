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
        context.getString(R.string.theme_1_name), theme2Name =
        context.getString(R.string.theme_2_name), theme3Name =
        context.getString(R.string.theme_3_name);

    if ((themeName = P.themeName.get()).contentEquals(theme0Name)) {
      return R.style.AppStandard;
    } else if (themeName.contentEquals(theme1Name)) {
      return R.style.AppNegative;
    } else if (themeName.contentEquals(theme2Name)) {
      return R.style.AppWeb;
    } else if (themeName.contentEquals(theme3Name)) {
      return R.style.AppWarm;
    } else {
      throw new IllegalStateException(
          String.format(Locale.ENGLISH, "Unrecognized theme name %s", themeName));
    }
  }

  @StyleRes public static int getSettingsTheme(final @NonNull Context context) {
    final String themeName, theme0Name = context.getString(R.string.theme_0_name), theme1Name =
        context.getString(R.string.theme_1_name), theme2Name =
        context.getString(R.string.theme_2_name), theme3Name =
        context.getString(R.string.theme_3_name);

    if ((themeName = P.themeName.get()).contentEquals(theme0Name)) {
      return R.style.SettingsStandard;
    } else if (themeName.contentEquals(theme1Name)) {
      return R.style.SettingsNegative;
    } else if (themeName.contentEquals(theme2Name)) {
      return R.style.SettingsWeb;
    } else if (theme3Name.contentEquals(theme3Name)) {
      return R.style.SettingsWarm;
    } else {
      throw new IllegalStateException(
          String.format(Locale.ENGLISH, "Unrecognized theme name %s", themeName));
    }
  }

  public static boolean isSelectedThemeDark(final @NonNull Context context) {
    final String themeName, theme0Name = context.getString(R.string.theme_0_name), theme2Name =
        context.getString(R.string.theme_2_name);

    return (themeName = P.themeName.get()).contentEquals(theme0Name) || themeName.contentEquals(
        theme2Name);
  }
}
