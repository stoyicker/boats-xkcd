package com.jorge.boats.xkcd.view.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.jorge.boats.xkcd.log.ApplicationLogger;

/**
 * See <a href="https://github.com/chrisbanes/PhotoView#issues-with-viewgroups">PhotoView - Issues
 * with ViewGroups</a>
 */
public class PhotoViewExceptionProofRelativeLayout extends RelativeLayout {

  private GestureDetector mIntermediateGestureDetector;

  public PhotoViewExceptionProofRelativeLayout(final @NonNull Context context) {
    super(context);
  }

  public PhotoViewExceptionProofRelativeLayout(final @NonNull Context context,
      final @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public PhotoViewExceptionProofRelativeLayout(final @NonNull Context context,
      final @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  public boolean onInterceptTouchEvent(final @NonNull MotionEvent ev) {
    try {
      return super.onInterceptTouchEvent(ev);
    } catch (final @NonNull IllegalArgumentException e) {
      ApplicationLogger.e(e, PhotoViewExceptionProofRelativeLayout.class.getCanonicalName());
      return false;
    }
  }

  @Override
  public boolean onTouchEvent(final @NonNull MotionEvent event) {
    if (mIntermediateGestureDetector != null) {
      return mIntermediateGestureDetector.onTouchEvent(event) || super.onTouchEvent(event);
    } else {
      return super.onTouchEvent(event);
    }
  }

  public void setIntermediateGestureDetector(final @NonNull GestureDetector gestureDetector) {
    this.mIntermediateGestureDetector = gestureDetector;
  }
}
