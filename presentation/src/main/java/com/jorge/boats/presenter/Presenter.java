package com.jorge.boats.presenter;

import android.support.annotation.NonNull;

/**
 * Interface representing a Presenter in a generate view presenter (MVP) pattern.
 */
interface Presenter<ViewType> {

  void setView(final @NonNull ViewType view);

  void resume();

  void pause();

  void destroy();
}
