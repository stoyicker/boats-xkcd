package com.jorge.boats.xkcd.util;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;

public class ViewServer {

  private static ViewServer instance;

  public static ViewServer get(final @Nullable Context context) {

    if (instance == null) instance = new ViewServer();

    return instance;
  }

  public void addWindow(final @Nullable Activity activity) {
    //Do nothing
  }

  public void removeWindow(final @Nullable Activity activity) {
    //Do nothing
  }

  public void setFocusedWindow(final @Nullable Activity activity) {
    //Do nothing
  }
}
