package com.jorge.boats.xkcd.domain.interactor;

import com.jorge.boats.xkcd.domain.executor.PostExecutionThread;
import com.jorge.boats.xkcd.domain.executor.ThreadExecutor;

import rx.Single;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

public abstract class SingleUseCase<T> {

  private final ThreadExecutor mThreadExecutor;
  private final PostExecutionThread mPostExecutionThread;

  private Subscription mSubscription = Subscriptions.empty();

  protected SingleUseCase(final ThreadExecutor threadExecutor,
      final PostExecutionThread postExecutionThread) {
    this.mThreadExecutor = threadExecutor;
    this.mPostExecutionThread = postExecutionThread;
  }

  protected abstract Single<T> buildUseCaseObservable();

  public void execute(final Subscriber<T> useCaseSubscriber) {
    this.mSubscription = this.buildUseCaseObservable()
        .subscribeOn(Schedulers.from(mThreadExecutor))
        .observeOn(mPostExecutionThread.getScheduler())
        .subscribe(useCaseSubscriber);
  }

  public void destroy() {
    if (!mSubscription.isUnsubscribed()) {
      mSubscription.unsubscribe();
    }
  }
}
