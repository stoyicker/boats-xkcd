package com.jorge.boats.xkcd.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

public interface ContentView {

  void showContent();

  void hideContent();

  void showLoading();

  void hideLoading();

  void showRetry(final @NonNull Throwable throwable);

  void hideRetry();

  Context getContext();

  void showMessage(final @StringRes int messageRes);
}
