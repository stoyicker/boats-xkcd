package com.jorge.boats.navigation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BaseInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.jorge.boats.R;
import com.jorge.boats.log.ApplicationLogger;

public class NavigationLayout extends LinearLayout {

  @Bind({ R.id.fab_index_zero, R.id.fab_index_one, R.id.fab_index_two }) View[] mButtons;

  private boolean isExpanded = false;

  public NavigationLayout(final @NonNull Context context, final @Nullable AttributeSet attrs) {
    super(context, attrs);

    init(context);
  }

  private void init(final @NonNull Context context) {
    if (!isInEditMode()) {
      LayoutInflater.from(context).inflate(R.layout.widget_navigation_layout, this);
    }

    ButterKnife.bind(this);
  }

  public boolean isExpanded() {
    return isExpanded;
  }

  private void toggleExpanded() {
    isExpanded = !isExpanded;
  }

  public boolean show() {
    ApplicationLogger.d("Checking navigation layout for show");
    if (isExpanded()) return false;

    animateIn();
    toggleExpanded();

    return true;
  }

  public boolean hide() {
    ApplicationLogger.d("Checking navigation layout for hide");
    if (!isExpanded()) return false;

    animateOut();
    toggleExpanded();

    return true;
  }

  private void animateIn() {
    ApplicationLogger.d("Animating navigation layout in");
    final Context context = getContext();
    final Resources resources = context.getResources();
    final boolean isLandscape =
        resources.getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    final int navigationLayoutPerButtonDelayMillis =
        resources.getInteger(R.integer.navigation_layout_element_animation_delay_milliseconds);
    final int animationDurationMillis =
        resources.getInteger(R.integer.navigation_layout_global_animation_duration_milliseconds);
    int delayMs = navigationLayoutPerButtonDelayMillis * mButtons.length;
    //Using AnimationSet does not work
    Animator rotation, translation;
    final BaseInterpolator interpolator = new DecelerateInterpolator();

    for (final View button : mButtons) {
      rotation = generateRotationAnimator(button);
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

  private void animateOut() {
    ApplicationLogger.d("Animating navigation layout out");
    final Context context = getContext();
    final Resources resources = context.getResources();
    final boolean isLandscape =
        resources.getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    final int navigationLayoutPerButtonDelayMillis =
        resources.getInteger(R.integer.navigation_layout_element_animation_delay_milliseconds);
    final int animationDurationMillis =
        resources.getInteger(R.integer.navigation_layout_global_animation_duration_milliseconds);
    int delayMs = navigationLayoutPerButtonDelayMillis * mButtons.length;
    //Using AnimationSet does not work
    Animator rotation, translation;
    final BaseInterpolator interpolator = new AccelerateInterpolator();

    for (final View button : mButtons) {
      rotation = generateRotationAnimator(button);
      translation = generateTranslateOutAnimator(button, isLandscape);

      translation.setDuration(animationDurationMillis);
      translation.setInterpolator(interpolator);
      translation.setStartDelay(delayMs);

      rotation.setDuration(animationDurationMillis);
      rotation.setInterpolator(interpolator);
      rotation.setStartDelay(delayMs);

      translation.start();
      rotation.start();

      delayMs -= navigationLayoutPerButtonDelayMillis;
    }
  }

  @OnClick(R.id.fab_index_zero) void navigateToPrevious() {
  }

  @OnClick(R.id.fab_index_one) void navigateToShare() {
  }

  @OnClick(R.id.fab_index_two) void navigateToNext() {
  }

  private static Animator generateRotationAnimator(final @NonNull View target) {
    final PropertyValuesHolder propertyValuesHolderRotation =
        PropertyValuesHolder.ofFloat(View.ROTATION, 0, 360);

    return ObjectAnimator.ofPropertyValuesHolder(target, propertyValuesHolderRotation);
  }

  private static final int TRANSLATION_TYPE_IN = -1, TRANSLATION_TYPE_OUT = 1;

  @IntDef({
      TRANSLATION_TYPE_IN, TRANSLATION_TYPE_OUT
  }) private @interface TranslationType {
  }

  private static Animator generateTranslateInAnimator(final @NonNull View target,
      final boolean isLandscape) {
    return generateTranslateAnimator(target, isLandscape, TRANSLATION_TYPE_IN);
  }

  private static Animator generateTranslateOutAnimator(final @NonNull View target,
      final boolean isLandscape) {
    return generateTranslateAnimator(target, isLandscape, TRANSLATION_TYPE_OUT);
  }

  private static Animator generateTranslateAnimator(final @NonNull View target,
      final boolean isLandscape, final @TranslationType int translation) {
    final PropertyValuesHolder propertyValuesHolderTranslation;
    final DisplayMetrics displayMetrics = new DisplayMetrics();

    ((WindowManager) target.getContext()
        .getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);

    if (isLandscape) {
      propertyValuesHolderTranslation = PropertyValuesHolder.ofFloat(View.Y,
          translation == TRANSLATION_TYPE_IN ? displayMetrics.heightPixels - target.getHeight() * 1.1f
              : displayMetrics.heightPixels);
    } else {
      propertyValuesHolderTranslation = PropertyValuesHolder.ofFloat(View.X,
          translation == TRANSLATION_TYPE_IN ? displayMetrics.widthPixels - target.getWidth() * 1.1f
              : displayMetrics.widthPixels);
    }

    return ObjectAnimator.ofPropertyValuesHolder(target, propertyValuesHolderTranslation);
  }
}