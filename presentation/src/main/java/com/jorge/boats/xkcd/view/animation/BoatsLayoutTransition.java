package com.jorge.boats.xkcd.view.animation;

import android.animation.LayoutTransition;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.jorge.boats.xkcd.R;

public class BoatsLayoutTransition extends LayoutTransition {

  private static final int TRANSLATION_DISTANCE_PX = 100;

  public BoatsLayoutTransition(final @NonNull Context context) {
    super();

    configure(context);
  }

  private void configure(final @NonNull Context context) {
    final int animationDurationMillis = context.getResources()
        .getInteger(R.integer.animation_duration_default_milliseconds);

    setAnimator(APPEARING,
        new FadeInSlideUpAnimator(animationDurationMillis, TRANSLATION_DISTANCE_PX).prepare(null)
            .getAnimatorAgent());
    setAnimator(DISAPPEARING,
        new FadeOutSlideDownAnimator(animationDurationMillis, TRANSLATION_DISTANCE_PX).prepare(null)
            .getAnimatorAgent());
    setInterpolator(APPEARING, new DecelerateInterpolator());
    setInterpolator(DISAPPEARING, new AccelerateInterpolator());
    setInterpolator(CHANGE_APPEARING, new AccelerateInterpolator());
    setInterpolator(CHANGE_DISAPPEARING, new DecelerateInterpolator());
    setInterpolator(CHANGING, new LinearInterpolator());
    setStagger(APPEARING, 0);
    setStagger(DISAPPEARING, 0);
    setStagger(CHANGE_APPEARING, 0);
    setStagger(CHANGE_DISAPPEARING, 0);
    setStagger(CHANGING, 0);
  }
}
