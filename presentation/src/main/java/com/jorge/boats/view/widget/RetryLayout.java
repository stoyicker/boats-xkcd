package com.jorge.boats.view.widget;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.jorge.boats.R;
import com.jorge.boats.presenter.StripePresenter;

public class RetryLayout extends LinearLayout {

  @Bind(android.R.id.text1) TextView mText;

  private StripePresenter mStripePresenter;

  public RetryLayout(final @NonNull Context context, final @Nullable AttributeSet attrs) {
    super(context, attrs);

    init(context);
  }

  private void init(final @NonNull Context context) {
    if (!isInEditMode()) {
      LayoutInflater.from(context).inflate(R.layout.widget_retry, this);
    }

    setOrientation(VERTICAL);

    setOnClickListener(new OnClickListener() {
      @Override public void onClick(final @NonNull View v) {
        RetryLayout.this.onClick();
      }
    });

    ButterKnife.bind(this);
  }

  public void setStripePresenter(final @NonNull StripePresenter stripePresenter) {
    this.mStripePresenter = stripePresenter;
  }

  private void onClick() {
    mStripePresenter.requestRetry();
  }

  public void setTextTypeface(final @NonNull Typeface typeface) {
    mText.setTypeface(typeface);
  }

  @Override public void setVisibility(final int visibility) {
    animate().alpha(visibility == VISIBLE ? 1 : 0)
        .setDuration(getResources().getInteger(R.integer.retry_fade_duration_milliseconds))
        .setListener(new Animator.AnimatorListener() {
          @Override public void onAnimationStart(final @NonNull Animator animation) {
            if (visibility == View.VISIBLE) RetryLayout.super.setVisibility(View.VISIBLE);
          }

          @Override public void onAnimationEnd(final @NonNull Animator animation) {
            if (visibility != View.VISIBLE) RetryLayout.super.setVisibility(visibility);
          }

          @Override public void onAnimationCancel(final @NonNull Animator animation) {

          }

          @Override public void onAnimationRepeat(final @NonNull Animator animation) {

          }
        });
  }
}
