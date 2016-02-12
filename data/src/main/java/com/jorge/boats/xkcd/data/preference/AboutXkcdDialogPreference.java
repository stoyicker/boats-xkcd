package com.jorge.boats.xkcd.data.preference;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.preference.DialogPreference;
import android.util.AttributeSet;

public class AboutXkcdDialogPreference extends DialogPreference {

  public AboutXkcdDialogPreference(final @NonNull Context context) {
    super(context);
  }

  public AboutXkcdDialogPreference(final @NonNull Context context,
      final @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public AboutXkcdDialogPreference(final @NonNull Context context,
      final @Nullable AttributeSet attrs, final int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public AboutXkcdDialogPreference(final @NonNull Context context,
      final @Nullable AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }
}
