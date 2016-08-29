package com.jorge.boats.xkcd.view.animation;

import android.animation.ObjectAnimator;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

public class FadeInSlideUpAnimator extends BaseViewAnimator {

  private final int mDistancePx;

  public FadeInSlideUpAnimator(final @IntRange(from = 0) int durationMillis, final int
      slideDistancePx) {
    super(durationMillis);
    mDistancePx = slideDistancePx;
  }

  @Override
  @NonNull
  public BaseViewAnimator prepare(final @Nullable View target) {
    getAnimatorAgent().playTogether(ObjectAnimator.ofFloat(target, "alpha", 0, 1),
        ObjectAnimator.ofFloat(target, "translationY", mDistancePx, 0));

    return super.prepare(target);
  }
}
