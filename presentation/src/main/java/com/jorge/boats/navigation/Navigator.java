package com.jorge.boats.navigation;

import android.support.annotation.IntDef;
import javax.inject.Singleton;

@Singleton public class Navigator {

  private static final int NAVIGATION_ITEM_SHARE = 0, NAVIGATION_ITEM_PREVIOUS = 1,
      NAVIGATION_ITEM_NEXT = 2;

  @IntDef({ NAVIGATION_ITEM_SHARE, NAVIGATION_ITEM_PREVIOUS, NAVIGATION_ITEM_NEXT })
  public @interface NavigationItem {
  }

  public void navigateToItem(final @NavigationItem int item) {
    //TODO navigateToItem
  }
}