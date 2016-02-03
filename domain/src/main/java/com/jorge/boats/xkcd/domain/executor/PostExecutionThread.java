package com.jorge.boats.xkcd.domain.executor;

import rx.Scheduler;

public interface PostExecutionThread {
  Scheduler getScheduler();
}