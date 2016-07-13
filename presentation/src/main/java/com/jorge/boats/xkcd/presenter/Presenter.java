package com.jorge.boats.xkcd.presenter;

import android.support.annotation.NonNull;

/**
 * Interface representing a Presenter in a generate view presenter (MVP) pattern.
 */
interface Presenter<ViewType> {

  void setStripeContentView(final @NonNull ViewType view);

  void resume();

  void pause();

  void destroy();

  void requestRetry();

  boolean isRetryViewShown();
}
