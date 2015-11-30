package com.jorge.boats.domain.executor;

import rx.Scheduler;

public interface PostExecutionThread {
  Scheduler getScheduler();
}