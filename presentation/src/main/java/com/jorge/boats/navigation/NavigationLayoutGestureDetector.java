package com.jorge.boats.navigation;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.WindowManager;
import com.jorge.boats.R;
import com.jorge.boats.di.PerActivity;
import com.jorge.boats.log.ApplicationLogger;
import java.util.Locale;
import javax.inject.Inject;

@PerActivity public class NavigationLayoutGestureDetector extends GestureDetector {

  @Inject public NavigationLayoutGestureDetector(final @NonNull Context context,
      final @NonNull NavigationLayout navigationLayout) {
    super(context, new NavigationLayoutBezelGestureDetectorListener(context, navigationLayout));
  }

  private static final class NavigationLayoutBezelGestureDetectorListener
      extends GestureDetector.SimpleOnGestureListener {

    private final float mBezelGestureSpanPixels;
    private final float mLayoutShowMinimumDistancePixels;
    private final float mLayoutHideMinimumDistancePixels;
    private final boolean isLandscape;
    private final NavigationLayout mNavigationLayout;
    private final int mScreenWidth;
    private final int mScreenHeight;

    private NavigationLayoutBezelGestureDetectorListener(final @NonNull Context context,
        final @NonNull NavigationLayout navigationLayout) {
      final Resources resources = context.getResources();

      isLandscape = resources.getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
      mNavigationLayout = navigationLayout;
      mBezelGestureSpanPixels = resources.getInteger(R.integer.bezel_gesture_span_pixels);
      mLayoutShowMinimumDistancePixels =
          resources.getInteger(R.integer.navigator_layout_show_minimum_distance_pixels);
      mLayoutHideMinimumDistancePixels =
          resources.getInteger(R.integer.navigator_layout_hide_minimum_distance_pixels);

      final DisplayMetrics displayMetrics = new DisplayMetrics();
      ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
          .getMetrics(displayMetrics);
      mScreenWidth = displayMetrics.widthPixels;
      mScreenHeight = displayMetrics.heightPixels;
    }

    @Override public boolean onFling(final @NonNull MotionEvent e1, final @NonNull MotionEvent e2,
        final float velocityX, final float velocityY) {
      if (e1.getAction() != MotionEvent.ACTION_DOWN || e2.getAction() != MotionEvent.ACTION_UP) {
        return false;
      }

      ApplicationLogger.d(
          String.format(Locale.ENGLISH, "FLING:\ne1: %.0f,%.0f\ne2: %.0f,%.0f", e1.getX(),
              e1.getY(), e2.getX(), e2.getY()));
      ApplicationLogger.d(String.format(Locale.ENGLISH, "MINIMUM SHOW DISTANCE: %.0f",
          mLayoutShowMinimumDistancePixels));
      ApplicationLogger.d(
          String.format(Locale.ENGLISH, "BEZEL GESTURE SPAN: %.0f", mBezelGestureSpanPixels));

      final boolean isGestureStartingFromCorrectBezel = isPositionedOnRelevantEndBezel(e1);
      final boolean isLayoutShowing = mNavigationLayout.isShown();
      final float start, end;

      if (isLayoutShowing || isGestureStartingFromCorrectBezel) {
        if (isLandscape) {
          start = e1.getY();
          end = e2.getY();
        } else {
          start = e1.getX();
          end = e2.getX();
        }

        if (!isLayoutShowing && start - end >= mLayoutShowMinimumDistancePixels) {
          mNavigationLayout.show();
        } else if (end - start >= mLayoutHideMinimumDistancePixels) {
          mNavigationLayout.hide();
        }
      }

      return false;
    }

    /**
     * Check is the gesture has started on the right (portrait) or bottom (landscape) bezels.
     */
    private boolean isPositionedOnRelevantEndBezel(final @NonNull MotionEvent event) {
      if (isLandscape) {
        return event.getY() + mBezelGestureSpanPixels >= mScreenHeight;
      } else {
        return event.getX() + mBezelGestureSpanPixels >= mScreenWidth;
      }
    }
  }
}
