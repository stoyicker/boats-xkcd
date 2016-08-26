package com.jorge.boats.xkcd;

import com.jorge.boats.xkcd.domain.executor.PostExecutionThread;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;

@Singleton
public class UIThread implements PostExecutionThread {

  @Inject
  public UIThread() {
  }

  @Override
  public Scheduler getScheduler() {
    return AndroidSchedulers.mainThread();
  }
}
