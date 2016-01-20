package com.jorge.boats.navigation;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.view.GestureDetector;
import android.view.MotionEvent;
import com.jorge.boats.R;
import com.jorge.boats.di.PerActivity;
import com.jorge.boats.log.ApplicationLogger;
import java.util.Locale;
import javax.inject.Inject;

@PerActivity public class NavigationLayoutGestureDetector extends GestureDetector {

  @Inject public NavigationLayoutGestureDetector(final @NonNull Context context,
      final @NonNull NavigationLayout navigationLayout) {
    super(context,
        new NavigationLayoutBezelGestureDetectorListener(context.getResources(), navigationLayout));
  }

  private static final class NavigationLayoutBezelGestureDetectorListener
      extends GestureDetector.SimpleOnGestureListener {

    private final float mBezelGestureSpanPixels;
    private final float mLayoutShowMinimumDistancePixels;
    private final float mLayoutHideMinimumDistancePixels;
    private final boolean isLandscape;
    private final NavigationLayout mNavigationLayout;

    private NavigationLayoutBezelGestureDetectorListener(final @NonNull Resources resources,
        final @NonNull NavigationLayout navigationLayout) {
      isLandscape = resources.getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
      mNavigationLayout = navigationLayout;
      mBezelGestureSpanPixels = resources.getInteger(R.integer.bezel_gesture_span_pixels);
      mLayoutShowMinimumDistancePixels =
          resources.getInteger(R.integer.navigator_layout_show_minimum_distance_pixels);
      mLayoutHideMinimumDistancePixels =
          resources.getInteger(R.integer.navigator_layout_hide_minimum_distance_pixels);
    }

    @Override public boolean onFling(final @NonNull MotionEvent e1, final @NonNull MotionEvent e2,
        final float velocityX, final float velocityY) {
      if (e2.getAction() != MotionEvent.ACTION_UP) return false;

      ApplicationLogger.d(
          String.format(Locale.ENGLISH, "FLING:\ne1: %.0f,%.0f\ne2: %.0f,%.0f", e1.getX(),
              e1.getY(), e2.getX(), e2.getY()));
      ApplicationLogger.d(String.format(Locale.ENGLISH, "MINIMUM SHOW DISTANCE: %.0f",
          mLayoutShowMinimumDistancePixels));
      ApplicationLogger.d(
          String.format(Locale.ENGLISH, "BEZEL GESTURE SPAN: %.0f", mBezelGestureSpanPixels));

      //TODO HERE: Include hide

      if (isLandscape) {
        if (e1.getY() + mBezelGestureSpanPixels >= mLayoutShowMinimumDistancePixels) {
          return mNavigationLayout.show();
        }
      } else {
        if (e1.getX() + mBezelGestureSpanPixels >= mLayoutShowMinimumDistancePixels) {
          return mNavigationLayout.show();
        }
      }

      return false;
    }
  }
}
