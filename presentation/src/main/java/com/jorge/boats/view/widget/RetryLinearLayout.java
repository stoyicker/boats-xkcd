package com.jorge.boats.view.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.jorge.boats.R;
import com.jorge.boats.presenter.StripePresenter;

public class RetryLinearLayout extends ListenableRippleLinearLayout {

  @Bind(android.R.id.text1) TextView mText;

  private StripePresenter mStripePresenter;

  public RetryLinearLayout(final @NonNull Context context, final @Nullable AttributeSet attrs) {
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
        RetryLinearLayout.this.onClick();
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
}
