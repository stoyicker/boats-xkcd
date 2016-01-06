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
