package com.jorge.boats.view;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import com.jorge.boats.R;
import com.jorge.boats.log.ApplicationLogger;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Map;
import java.util.WeakHashMap;

public abstract class FontManager {

  public static final int FONT_APP = R.string.app_font;

  @IntDef({ FONT_APP }) @Retention(RetentionPolicy.CLASS) public @interface Font {
  }

  private FontManager() {
    throw new IllegalAccessError("No instances.");
  }

  private static final Map<String, Typeface> FONT_CACHE = new WeakHashMap<>();

  private static Typeface get(final @NonNull Context context, final @NonNull String name) {
    Typeface tf = FONT_CACHE.get(name);
    if (tf == null) {
      try {
        tf = Typeface.createFromAsset(context.getAssets(), name);
      } catch (final @NonNull Exception e) {
        ApplicationLogger.e(e, e.getClass().getName());
        return null;
      }
      FONT_CACHE.put(name, tf);
    }
    return tf;
  }

  public static Typeface get(final @NonNull Context context, final @Font int font) {
    return get(context, context.getString(font));
  }
}