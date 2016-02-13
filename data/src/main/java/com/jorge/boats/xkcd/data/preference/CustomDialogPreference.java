package com.jorge.boats.xkcd.data.preference;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.preference.DialogPreference;
import android.util.AttributeSet;

public abstract class CustomDialogPreference extends DialogPreference {

  public CustomDialogPreference(final @NonNull Context context) {
    super(context);
  }

  public CustomDialogPreference(final @NonNull Context context,
      final @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public CustomDialogPreference(final @NonNull Context context, final @Nullable AttributeSet attrs,
      final int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public CustomDialogPreference(final @NonNull Context context, final @Nullable AttributeSet attrs,
      final int defStyleAttr, final int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  public abstract Dialog buildDialog();
}
