/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jorge.boats.domain.interactor;

import com.jorge.boats.domain.executor.PostExecutionThread;
import com.jorge.boats.domain.executor.ThreadExecutor;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

public abstract class UseCase {

  private final ThreadExecutor mThreadExecutor;
  private final PostExecutionThread mPostExecutionThread;

  private Subscription mSubscription = Subscriptions.empty();

  protected UseCase(final ThreadExecutor threadExecutor,
      final PostExecutionThread postExecutionThread) {
    this.mThreadExecutor = threadExecutor;
    this.mPostExecutionThread = postExecutionThread;
  }

  protected abstract Observable<Object> buildUseCaseObservable();

  public void execute(final Subscriber useCaseSubscriber) {
    //noinspection unchecked
    this.buildUseCaseObservable()
        .subscribeOn(Schedulers.from(mThreadExecutor))
        .observeOn(mPostExecutionThread.getScheduler())
        .subscribe(useCaseSubscriber);
  }

  public void unsubscribe() {
    if (!mSubscription.isUnsubscribed()) {
      mSubscription.unsubscribe();
    }
  }
}
