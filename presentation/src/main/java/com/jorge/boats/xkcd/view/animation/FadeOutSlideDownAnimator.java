package com.jorge.boats.xkcd.view.animation;

import android.animation.ObjectAnimator;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

public class FadeOutSlideDownAnimator extends BaseViewAnimator {

  private final int mDistancePx;

  public FadeOutSlideDownAnimator(final @IntRange(from = 0) int durationMillis, final int
      slideDistancePx) {
    super(durationMillis);
    mDistancePx = slideDistancePx;
  }

  @Override
  @NonNull
  public BaseViewAnimator prepare(final @Nullable View target) {
    getAnimatorAgent().playTogether(ObjectAnimator.ofFloat(target, "alpha", 1, 0),
        ObjectAnimator.ofFloat(target, "translationY", 0, mDistancePx));

    return super.prepare(target);
  }
}
