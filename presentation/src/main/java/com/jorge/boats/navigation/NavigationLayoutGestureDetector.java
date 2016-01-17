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

      final long availableSpace =
          isLandscape ? Resources.getSystem().getDisplayMetrics().heightPixels
              : Resources.getSystem().getDisplayMetrics().widthPixels;

      ApplicationLogger.d(
          String.format(Locale.ENGLISH, "FLING:\ne1: %.0f,%.0f\ne2: %.0f,%.0f", e1.getX(),
              e1.getY(), e2.getX(), e2.getY()));

      final float start;

      //TODO HERE: Enhance the condition and include hide
      //TODO NOT HERE: Change the layout to the new appearance

      if (isLandscape) {
        if ((start = e1.getY()) + mBezelGestureSpanPixels >= availableSpace
            && e2.getY() + mLayoutShowMinimumDistancePixels < start) {
          return mNavigationLayout.show();
        }
      } else {
        if ((start = e1.getX()) + mBezelGestureSpanPixels >= availableSpace
            && e2.getX() + mLayoutShowMinimumDistancePixels < start) {
          return mNavigationLayout.show();
        }
      }

      return false;
    }
  }
}
