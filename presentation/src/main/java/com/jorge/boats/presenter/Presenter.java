package com.jorge.boats.presenter;

/**
 * Interface representing a Presenter in a generate view presenter (MVP) pattern.
 */
public interface Presenter {

  void resume();

  void pause();

  void destroy();
}
