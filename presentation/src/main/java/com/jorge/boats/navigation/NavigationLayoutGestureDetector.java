package com.jorge.boats.navigation;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import com.jorge.boats.di.PerActivity;
import javax.inject.Inject;

@PerActivity public class NavigationLayoutGestureDetector extends GestureDetector {

  @Inject public NavigationLayoutGestureDetector(final @NonNull Context context,
      final @NonNull NavigationLayout navigationLayout) {
    super(context, new NavigationLayoutBezelGestureDetectorListener(
        context.getResources().getConfiguration().orientation
            == Configuration.ORIENTATION_LANDSCAPE, navigationLayout));
  }

  private static final class NavigationLayoutBezelGestureDetectorListener
      extends GestureDetector.SimpleOnGestureListener {

    private static final float BEZEL_GESTURE_SPAN_PIXELS = 500;
    private final boolean isLandscape;
    private final NavigationLayout mNavigationLayout;

    private NavigationLayoutBezelGestureDetectorListener(final boolean isLandscape,
        final @NonNull NavigationLayout navigationLayout) {
      this.isLandscape = isLandscape;
      mNavigationLayout = navigationLayout;
    }

    @Override public boolean onFling(final @NonNull MotionEvent e1, final @NonNull MotionEvent e2,
        final float velocityX, final float velocityY) {
      final long availableSpace =
          isLandscape ? Resources.getSystem().getDisplayMetrics().widthPixels
              : Resources.getSystem().getDisplayMetrics().heightPixels;

      Log.d("JORGETEST", "FLING");

      return (isLandscape ? e1.getX() : e1.getY()) + BEZEL_GESTURE_SPAN_PIXELS >= availableSpace
          && mNavigationLayout.show();
    }
  }
}
