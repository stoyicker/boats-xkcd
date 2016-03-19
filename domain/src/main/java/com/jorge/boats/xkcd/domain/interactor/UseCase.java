package com.jorge.boats.xkcd.domain.interactor;

import com.jorge.boats.xkcd.domain.executor.PostExecutionThread;
import com.jorge.boats.xkcd.domain.executor.ThreadExecutor;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

public abstract class UseCase<T> {

    private final ThreadExecutor mThreadExecutor;
    private final PostExecutionThread mPostExecutionThread;

    private Subscription mSubscription = Subscriptions.empty();

    protected UseCase(final ThreadExecutor threadExecutor,
                      final PostExecutionThread postExecutionThread) {
        this.mThreadExecutor = threadExecutor;
        this.mPostExecutionThread = postExecutionThread;
    }

    protected abstract Observable<T> buildUseCaseObservable();

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
