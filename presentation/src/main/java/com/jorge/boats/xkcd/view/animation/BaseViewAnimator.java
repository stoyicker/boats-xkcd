package com.jorge.boats.xkcd.view.animation;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.Interpolator;

public abstract class BaseViewAnimator {

  private long mDurationMillis;
  private final AnimatorSet mAnimatorSet = new AnimatorSet();

  BaseViewAnimator(final @IntRange(from = 0) int durationMillis) {
    mDurationMillis = durationMillis;
  }

  @NonNull
  BaseViewAnimator prepare(final @Nullable View target) {
    mAnimatorSet.setDuration(mDurationMillis);
    return this;
  }

  public BaseViewAnimator setTarget(final @NonNull View target) {
    reset(target);
    prepare(target);
    return this;
  }

  public void reset(final @NonNull View target) {
    target.setAlpha(1);
    target.setScaleX(1);
    target.setScaleY(1);
    target.setTranslationX(0);
    target.setTranslationY(0);
    target.setRotation(0);
    target.setRotationY(0);
    target.setRotationX(0);
    target.setPivotX(target.getMeasuredWidth() / 2.0f);
    target.setPivotY(target.getMeasuredHeight() / 2.0f);
  }

  public void start() {
    mAnimatorSet.start();
  }

  @NonNull
  public BaseViewAnimator setDuration(final long durationMillis) {
    mDurationMillis = durationMillis;
    return this;
  }

  @NonNull
  public BaseViewAnimator setStartDelay(final long delay) {
    getAnimatorAgent().setStartDelay(delay);
    return this;
  }

  public long getStartDelay() {
    return mAnimatorSet.getStartDelay();
  }

  @NonNull
  public BaseViewAnimator addAnimatorListener(final @NonNull Animator.AnimatorListener l) {
    mAnimatorSet.addListener(l);
    return this;
  }

  public void cancel() {
    mAnimatorSet.cancel();
  }

  public boolean isRunning() {
    return mAnimatorSet.isRunning();
  }

  public boolean isStarted() {
    return mAnimatorSet.isStarted();
  }

  public void removeAnimatorListener(final @NonNull Animator.AnimatorListener l) {
    mAnimatorSet.removeListener(l);
  }

  public void removeAllListener() {
    mAnimatorSet.removeAllListeners();
  }

  @NonNull
  public BaseViewAnimator setInterpolator(final Interpolator interpolator) {
    mAnimatorSet.setInterpolator(interpolator);
    return this;
  }

  public long getDuration() {
    return mDurationMillis;
  }

  @NonNull
  public AnimatorSet getAnimatorAgent() {
    return mAnimatorSet;
  }
}
