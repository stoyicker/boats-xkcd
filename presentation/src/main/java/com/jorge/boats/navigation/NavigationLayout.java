package com.jorge.boats.navigation;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

public class NavigationLayout extends LinearLayout {

  private static final int NAVIGATION_ITEM_SHARE = 0, NAVIGATION_ITEM_PREVIOUS = 1,
      NAVIGATION_ITEM_NEXT = 2;

  public NavigationLayout(final @NonNull Context context, final @Nullable AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  private void init() {
    setVisibility(GONE);
    //TODO Remove this as it is for debugging purposes only
    setBackgroundColor(Color.RED);

    //addPreviousStripeView();
    //addShareView();
    //addNextStripeView();
  }

  public boolean show() {
    if (isShown()) return false;

    animateIn();

    return true;
  }

  public boolean hide() {
    if (!isShown()) return false;

    animateOut();

    return true;
  }

  private void animateIn() {
    Log.d("JORGETEST", "Animating in");
    final int childAmount = getChildCount();
    View currentChild;
    for (int i = 0; i < childAmount; i++) {
      //TODO Apply the in animation
    }
  }

  private void animateOut() {
    Log.d("JORGETEST", "Animating out");
    final int childAmount = getChildCount();
    View currentChild;
    for (int i = 0; i < childAmount; i++) {
      //TODO Apply the out animation
    }
  }

  @IntDef({ NAVIGATION_ITEM_SHARE, NAVIGATION_ITEM_PREVIOUS, NAVIGATION_ITEM_NEXT })
  public @interface NavigationItem {
  }

  public void navigateToItem(final @NavigationItem int item) {
    //TODO navigateToItem
  }
}