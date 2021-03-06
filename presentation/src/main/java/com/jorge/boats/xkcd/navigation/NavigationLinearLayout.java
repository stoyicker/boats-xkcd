package com.jorge.boats.xkcd.navigation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.support.annotation.ColorInt;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;

import com.jorge.boats.xkcd.R;
import com.jorge.boats.xkcd.presenter.StripePresenter;
import com.jorge.boats.xkcd.util.ThemeUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class  NavigationLinearLayout extends LinearLayout {

  @Bind({ R.id.fab_index_zero, R.id.fab_index_one, R.id.fab_index_two })
  FloatingActionButton[] mButtons;

  private boolean isExpanded = false;

  private StripePresenter mStripePresenter;
  private boolean isShownAsTutorial;

  public NavigationLinearLayout(final @NonNull Context context,
      final @Nullable AttributeSet attrs) {
    super(context, attrs);

    init(context);
  }

  private void init(final @NonNull Context context) {
    if (!isInEditMode()) {
      LayoutInflater.from(context).inflate(R.layout.widget_navigation_layout, this);
    }

    ButterKnife.bind(this);
    tintButtonIcons();
  }

  private void tintButtonIcons() {
    final @ColorInt int colorAccent = ContextCompat.getColor(getContext(),
            ThemeUtil.getAppColor(getContext()));

    for (final FloatingActionButton button : mButtons) {
      button.getDrawable().setColorFilter(colorAccent, PorterDuff.Mode.SRC_IN);
    }
  }

  public boolean isExpanded() {
    return isExpanded;
  }

  private void toggleExpanded() {
    isExpanded = !isExpanded;
  }

  public boolean show() {
    if (isShownAsTutorial || isExpanded() || mStripePresenter.isRetryViewShown()) {
      return false;
    }

    animateIn();
    toggleExpanded();

    return true;
  }

  public boolean showTutorial() {
    final boolean ret = show();

    isShownAsTutorial = ret;

    return ret;
  }

  public boolean hide() {
    return hide(getContext().getResources()
        .getInteger(R.integer.navigation_layout_global_animation_duration_milliseconds));
  }

  public boolean hideTutorial() {
    return hide(getContext().getResources()
            .getInteger(R.integer.navigation_layout_tutorial_hide_animation_duration_milliseconds),
        true);
  }

  private boolean hide(final int durationMillis) {
    return hide(durationMillis, false);
  }

  private boolean hide(final int durationMillis, final boolean force) {
    if (!force && (isShownAsTutorial || !isExpanded())) {
      return false;
    }

    animateOut(durationMillis);
    toggleExpanded();

    return true;
  }

  private void animateIn() {
    final Context context = getContext();
    final Resources resources = context.getResources();
    final boolean isLandscape =
        resources.getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    final int navigationLayoutPerButtonDelayMillis =
        resources.getInteger(R.integer.navigation_layout_element_animation_delay_milliseconds);
    final int animationDurationMillis =
        resources.getInteger(R.integer.navigation_layout_global_animation_duration_milliseconds);
    int delayMs = 0;
    //Using AnimationSet does not work
    Animator rotation, translation;
    final OvershootInterpolator interpolator = new OvershootInterpolator();

    for (final View button : mButtons) {
      rotation = generateRotationInAnimator(button);
      translation = generateTranslateInAnimator(button, isLandscape);

      translation.setDuration(animationDurationMillis);
      translation.setInterpolator(interpolator);
      translation.setStartDelay(delayMs);

      rotation.setDuration(animationDurationMillis);
      rotation.setInterpolator(interpolator);
      rotation.setStartDelay(delayMs);

      translation.start();
      rotation.start();

      delayMs += navigationLayoutPerButtonDelayMillis;
    }
  }

  private void animateOut(final int animationDurationMillis) {
    final Context context = getContext();
    final Resources resources = context.getResources();
    final boolean isLandscape =
        resources.getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    final int navigationLayoutPerButtonDelayMillis =
        resources.getInteger(R.integer.navigation_layout_element_animation_delay_milliseconds);
    int delayMs = navigationLayoutPerButtonDelayMillis * (mButtons.length - 1);
    //Using AnimationSet does not work
    Animator rotation, translation;
    final AnticipateInterpolator interpolator = new AnticipateInterpolator();

    for (final View button : mButtons) {
      rotation = generateRotationOutAnimator(button);
      translation = generateTranslateOutAnimator(button, isLandscape);

      translation.setDuration(animationDurationMillis);
      translation.setInterpolator(interpolator);
      translation.setStartDelay(delayMs);
      if (mButtons[mButtons.length - 1] == button) {
        translation.addListener(new Animator.AnimatorListener() {
          @Override
          public void onAnimationStart(Animator animation) {

          }

          @Override
          public void onAnimationEnd(Animator animation) {
            isShownAsTutorial = false;
          }

          @Override
          public void onAnimationCancel(Animator animation) {

          }

          @Override
          public void onAnimationRepeat(Animator animation) {

          }
        });
      }

      rotation.setDuration(animationDurationMillis);
      rotation.setInterpolator(interpolator);
      rotation.setStartDelay(delayMs);

      translation.start();
      rotation.start();

      delayMs -= navigationLayoutPerButtonDelayMillis;
    }
  }

  @OnClick(R.id.fab_index_zero)
  void navigateToPrevious() {
    mStripePresenter.actionPrevious();
    hide();
  }

  @OnClick(R.id.fab_index_one)
  void navigateToRandom() {
    mStripePresenter.actionRandom();
    hide();
  }

  @OnClick(R.id.fab_index_two)
  void navigateToNext() {
    mStripePresenter.actionNext();
    hide();
  }

  private static final int ANIMATOR_TYPE_IN = -1, ANIMATOR_TYPE_OUT = 1;

  public void setStripePresenter(final @NonNull StripePresenter stripePresenter) {
    this.mStripePresenter = stripePresenter;
  }

  @IntDef({
      ANIMATOR_TYPE_IN, ANIMATOR_TYPE_OUT
  })
  private @interface AnimatorType {
  }

  private static Animator generateRotationInAnimator(final @NonNull View target) {
    return generateRotationAnimator(target, ANIMATOR_TYPE_IN);
  }

  private static Animator generateRotationOutAnimator(final @NonNull View target) {
    return generateRotationAnimator(target, ANIMATOR_TYPE_OUT);
  }

  private static Animator generateRotationAnimator(final @NonNull View target,
      final @AnimatorType int translation) {
    final boolean isIn;
    final PropertyValuesHolder propertyValuesHolderRotation =
        PropertyValuesHolder.ofFloat(View.ROTATION,
            (isIn = translation == ANIMATOR_TYPE_IN) ? 0 : 720, isIn ? 720 : 0);

    return ObjectAnimator.ofPropertyValuesHolder(target, propertyValuesHolderRotation);
  }

  private static Animator generateTranslateInAnimator(final @NonNull View target,
      final boolean isLandscape) {
    return generateTranslateAnimator(target, isLandscape, ANIMATOR_TYPE_IN);
  }

  private static Animator generateTranslateOutAnimator(final @NonNull View target,
      final boolean isLandscape) {
    return generateTranslateAnimator(target, isLandscape, ANIMATOR_TYPE_OUT);
  }

  private static Animator generateTranslateAnimator(final @NonNull View target,
      final boolean isLandscape, final @AnimatorType int translation) {
    final PropertyValuesHolder propertyValuesHolderTranslation;
    final DisplayMetrics displayMetrics = new DisplayMetrics();
    final Context context;

    ((WindowManager) (context = target.getContext()).getSystemService(
        Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);

    if (isLandscape) {
      propertyValuesHolderTranslation = PropertyValuesHolder.ofFloat(View.Y,
          (translation == ANIMATOR_TYPE_IN ? displayMetrics.heightPixels - getWingspan(true, target)
              : displayMetrics.heightPixels) - getStatusBarHeight(context));
    } else {
      propertyValuesHolderTranslation = PropertyValuesHolder.ofFloat(View.X,
          translation == ANIMATOR_TYPE_IN ? displayMetrics.widthPixels - getWingspan(false, target)
              : displayMetrics.widthPixels);
    }

    return ObjectAnimator.ofPropertyValuesHolder(target, propertyValuesHolderTranslation);
  }

  private static int getWingspan(final boolean isLandscape, final @NonNull View view) {
    return (isLandscape ? view.getHeight() : view.getWidth()) + view.getContext()
        .getResources()
        .getDimensionPixelSize(R.dimen.standard_margin);
  }

  private static int getStatusBarHeight(final @NonNull Context context) {
    final Resources resources = context.getResources();
    int result = 0;
    int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");

    if (resourceId > 0) {
      result = resources.getDimensionPixelSize(resourceId);
    }
    return result;
  }
}
