/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.jorge.boats.view;

import android.content.Context;

public interface LoadStripeView {

  void showLoading();

  void hideLoading();

  void showRetry();

  void hideRetry();

  void showError(final Throwable throwable);

  Context getContext();
}
