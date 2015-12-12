/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.jorge.boats.view;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface LoadStripeView {

  int LOAD_STRIPE_ERROR_UNKNOWN = 0;

  @IntDef({ LOAD_STRIPE_ERROR_UNKNOWN }) @Retention(RetentionPolicy.CLASS)
  @interface LoadStripeError {
  }

  void showLoading();

  void hideLoading();

  void showRetry();

  void hideRetry();

  void showError(final @LoadStripeError int errorCode);

  void setToolbarTitleTypeface(final @NonNull Typeface typeface);

  Context getContext();
}
