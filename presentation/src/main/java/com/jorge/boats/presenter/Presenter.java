package com.jorge.boats.presenter;

/**
 * Interface representing a Presenter in a model view presenter (MVP) pattern.
 */
public interface Presenter {

  void resume();

  void pause();

  void destroy();
}
