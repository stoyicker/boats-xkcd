package com.jorge.boats.xkcd.navigation;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.WindowManager;

import com.jorge.boats.xkcd.R;
import com.jorge.boats.xkcd.data.P;
import com.jorge.boats.xkcd.di.PerActivity;

import javax.inject.Inject;

@PerActivity
public class NavigationGestureDetector extends GestureDetector {

  @Inject
  public NavigationGestureDetector(final @NonNull Context context,
      final @NonNull NavigationLinearLayout navigationLayout) {
    super(context, new NavigationLayoutBezelGestureDetectorListener(context, navigationLayout));
  }

  private static final class NavigationLayoutBezelGestureDetectorListener
      extends GestureDetector.SimpleOnGestureListener {

    private final float mBezelGestureSpanPixels;
    private final float mLayoutShowMinimumDistancePixels;
    private final float mLayoutHideMinimumDistancePixels;
    private final boolean isLandscape;
    private final NavigationLinearLayout mNavigationLayout;
    private final int mScreenWidth;
    private final int mScreenHeight;
    private final int mNavigationSwipeTimeLimitMillis;
    private final int mNavigationSwipeMinimumLength;

    private NavigationLayoutBezelGestureDetectorListener(final @NonNull Context context,
        final @NonNull NavigationLinearLayout navigationLayout) {
      final Resources resources = context.getResources();

      isLandscape = resources.getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
      mNavigationLayout = navigationLayout;
      mBezelGestureSpanPixels = resources.getInteger(R.integer.bezel_gesture_span_pixels);
      mLayoutShowMinimumDistancePixels =
          resources.getInteger(R.integer.navigation_layout_show_minimum_distance_pixels);
      mLayoutHideMinimumDistancePixels =
          resources.getInteger(R.integer.navigation_layout_hide_minimum_distance_pixels);
      mNavigationSwipeTimeLimitMillis =
          resources.getInteger(R.integer.navigation_swipe_time_limit_millis);
      mNavigationSwipeMinimumLength =
          resources.getInteger(R.integer.navigation_swipe_minimum_length);

      final DisplayMetrics displayMetrics = new DisplayMetrics();
      ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
          .getMetrics(displayMetrics);
      mScreenWidth = displayMetrics.widthPixels;
      mScreenHeight = displayMetrics.heightPixels;
    }

    @Override
    public boolean onFling(final @NonNull MotionEvent e1, final @NonNull MotionEvent e2,
        final float velocityX, final float velocityY) {
      if (e1.getAction() != MotionEvent.ACTION_DOWN || e2.getAction() != MotionEvent.ACTION_UP) {
        return false;
      }

      final boolean isGestureStartingFromCorrectBezel = isPositionedOnRelevantEndBezel(e1);
      final boolean isLayoutExpandedAndThusIDontNeedTheGestureToStartFromTheBezel =
          mNavigationLayout.isExpanded();

      if (isLayoutExpandedAndThusIDontNeedTheGestureToStartFromTheBezel
          || isGestureStartingFromCorrectBezel || !P.swipeControlNavigationEnabled.get()
          || !checkForNavigationSwipe(e1, e2)) {
        final float start, end;

        if (isLayoutExpandedAndThusIDontNeedTheGestureToStartFromTheBezel
            || isGestureStartingFromCorrectBezel) {
          if (isLandscape) {
            start = e1.getRawY();
            end = e2.getRawY();
          } else {
            start = e1.getRawX();
            end = e2.getRawX();
          }

          if (!isLayoutExpandedAndThusIDontNeedTheGestureToStartFromTheBezel
              && start - end >= mLayoutShowMinimumDistancePixels) {
            return mNavigationLayout.show();
          } else if (end - start >= mLayoutHideMinimumDistancePixels) {
            return mNavigationLayout.hide();
          }
        }
      } else {
        return true;
      }

      return false;
    }

    private boolean checkForNavigationSwipe(final @NonNull MotionEvent e1,
        final @NonNull MotionEvent e2) {
      if (e2.getEventTime() - e1.getEventTime() <= mNavigationSwipeTimeLimitMillis) {
        if (e1.getPointerCount() == 1) {
          final float difference;
          if (Math.abs(difference = e2.getRawX() - e1.getRawX()) > mNavigationSwipeMinimumLength) {
            if (difference > 0) {
              mNavigationLayout.navigateToPrevious();
            } else {
              mNavigationLayout.navigateToNext();
            }
            return true;
          }
        }
      }

      return false;
    }

    /**
     * Check is the gesture has started on the right (portrait) or bottom (landscape) bezels.
     */
    private boolean isPositionedOnRelevantEndBezel(final @NonNull MotionEvent event) {
      if (isLandscape) {
        return event.getRawY() + mBezelGestureSpanPixels >= mScreenHeight;
      } else {
        return event.getRawX() + mBezelGestureSpanPixels >= mScreenWidth;
      }
    }
  }
}
