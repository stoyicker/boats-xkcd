package com.jorge.boats.navigation;

import android.support.annotation.IntDef;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton public class Navigator {

  public static final int NAVIGATION_ITEM_SETTINGS = 0;

  @IntDef({ NAVIGATION_ITEM_SETTINGS }) public @interface NavigationItem {
  }

  @SuppressWarnings("MethodNameSameAsClassName") @Inject public void Navigator() {
  }

  public void navigateToItem(final @NavigationItem int item) {
    //TODO navigateToItem
  }
}