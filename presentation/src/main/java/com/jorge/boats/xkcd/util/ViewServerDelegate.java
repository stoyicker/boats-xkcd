package com.jorge.boats.xkcd.util;

import android.app.Activity;
import android.support.annotation.NonNull;

public abstract class ViewServerDelegate {

  private ViewServerDelegate() {
    throw new IllegalAccessError("No instances");
  }

  public static void addWindow(final @NonNull Activity activity) {
    ViewServer.get(activity).addWindow(activity);
  }

  public static void removeWindow(final @NonNull Activity activity) {
    ViewServer.get(activity).removeWindow(activity);
  }

  public static void setFocusedWindow(final @NonNull Activity activity) {
    ViewServer.get(activity).setFocusedWindow(activity);
  }
}
