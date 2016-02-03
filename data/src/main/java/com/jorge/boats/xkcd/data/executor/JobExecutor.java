package com.jorge.boats.xkcd.data.executor;

import android.support.annotation.NonNull;
import com.jorge.boats.xkcd.domain.executor.ThreadExecutor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton public class JobExecutor implements ThreadExecutor {

  private static final int INITIAL_POOL_SIZE = 3;
  private static final int MAX_POOL_SIZE = 5;

  // Sets the amount of time an idle thread waits before terminating
  private static final int KEEP_ALIVE_TIME = 10;

  // Sets the Time Unit to seconds
  private static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;

  private final ThreadPoolExecutor mThreadPoolExecutor;

  @Inject public JobExecutor() {
    this.mThreadPoolExecutor =
        new ThreadPoolExecutor(INITIAL_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME,
            KEEP_ALIVE_TIME_UNIT, new LinkedBlockingQueue<Runnable>(), new JobThreadFactory());
  }

  @Override public void execute(final @NonNull Runnable runnable) {
    this.mThreadPoolExecutor.execute(runnable);
  }

  private static class JobThreadFactory implements ThreadFactory {
    private static final String THREAD_NAME = "android_";
    private int mCounter = -1;

    @Override public synchronized Thread newThread(final @NonNull Runnable runnable) {
      return new Thread(runnable, THREAD_NAME + ++mCounter);
    }
  }
}
