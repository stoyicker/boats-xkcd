package com.jorge.boats.xkcd.util;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;

import com.jorge.boats.xkcd.R;
import com.jorge.boats.xkcd.data.P;

import java.util.Locale;

public abstract class ThemeUtil {

  private ThemeUtil() {
    throw new IllegalAccessError("No instances.");
  }

  @StyleRes
  public static int getAppTheme(final @NonNull Context context) {
    final String themeName, theme0Name = context.getString(R.string.theme_0_name), theme1Name =
        context.getString(R.string.theme_1_name), theme2Name =
        context.getString(R.string.theme_2_name), theme3Name =
        context.getString(R.string.theme_3_name), theme4Name = context.getString(
        R.string.theme_4_name), theme5Name = context.getString(R.string.theme_5_name);

    if ((themeName = P.themeName.get()).contentEquals(theme0Name)) {
      return R.style.AppStandard;
    } else if (themeName.contentEquals(theme1Name)) {
      return R.style.AppNegative;
    } else if (themeName.contentEquals(theme2Name)) {
      return R.style.AppWeb;
    } else if (themeName.contentEquals(theme3Name)) {
      return R.style.AppWarm;
    } else if (themeName.contentEquals(theme4Name)) {
      return R.style.AppMaterialLight;
    } else if (themeName.contentEquals(theme5Name)) {
      return R.style.AppMaterialDark;
    } else {
      throw new IllegalStateException(
          String.format(Locale.ENGLISH, "Unrecognized theme name %s", themeName));
    }
  }

  @StyleRes
  public static int getSettingsTheme(final @NonNull Context context) {
    final String themeName, theme0Name = context.getString(R.string.theme_0_name), theme1Name =
        context.getString(R.string.theme_1_name), theme2Name =
        context.getString(R.string.theme_2_name), theme3Name =
        context.getString(R.string.theme_3_name), theme4Name = context.getString(R.string
        .theme_4_name), theme5Name = context.getString(R.string.theme_5_name);

    if ((themeName = P.themeName.get()).contentEquals(theme0Name)) {
          return R.style.SettingsStandard;
      } else if (themeName.contentEquals(theme1Name)) {
          return R.style.SettingsNegative;
      } else if (themeName.contentEquals(theme2Name)) {
          return R.style.SettingsWeb;
      } else if (themeName.contentEquals(theme3Name)) {
          return R.style.SettingsWarm;
      } else if (themeName.contentEquals(theme4Name)) {
          return R.style.SettingsMaterialLight;
      } else if (themeName.contentEquals(theme5Name)) {
          return R.style.SettingsMaterialDark;
      } else {
          throw new IllegalStateException(
                  String.format(Locale.ENGLISH, "Unrecognized theme name %s", themeName));
      }
  }

  public static boolean isAppThemeLight(final @NonNull Context context) {
    final String themeName, theme0Name = context.getString(R.string.theme_0_name), theme2Name =
        context.getString(R.string.theme_2_name), theme4Name = context.getString(R.string
        .theme_4_name), theme5Name = context.getString(R.string.theme_5_name);

    return (themeName = P.themeName.get()).contentEquals(theme0Name) || themeName.contentEquals(
        theme2Name) || themeName.contentEquals(theme4Name) || themeName.contentEquals(theme5Name);
  }

  public static boolean isSettingsThemeDark(final @NonNull Context context) {
    return !isAppThemeLight(context);
  }

  @ColorRes
  public static int getAppColor(@NonNull final Context context) {
    switch(getAppTheme(context)) {
      case R.style.AppStandard:
        return R.color.standard_primary_light;
      case R.style.AppWeb:
        return R.color.web_primary_light;
      case R.style.AppWarm:
        return R.color.warm_primary_light;
      case R.style.AppNegative:
        return R.color.negative_primary_light;
      case R.style.AppMaterialLight:
        return R.color.material_light_primary_light;
      case R.style.AppMaterialDark:
        return R.color.material_dark_primary_light;
      default:
        throw new IllegalStateException("Theme not recognized.");
    }
  }
}
