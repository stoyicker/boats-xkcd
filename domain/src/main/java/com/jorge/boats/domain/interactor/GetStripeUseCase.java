package com.jorge.boats.domain.interactor;

import com.jorge.boats.domain.entity.DomainStripe;
import com.jorge.boats.domain.executor.PostExecutionThread;
import com.jorge.boats.domain.executor.ThreadExecutor;
import com.jorge.boats.domain.repository.XkcdStore;
import javax.inject.Inject;
import rx.Observable;

public class GetStripeUseCase extends UseCase<DomainStripe> {

  private final long mStripeNum;
  private final XkcdStore mStore;

  @Inject public GetStripeUseCase(final long stripeNum, final XkcdStore store,
      final ThreadExecutor threadExecutor, final PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    mStripeNum = stripeNum;
    mStore = store;
  }

  @Override protected Observable<DomainStripe> buildUseCaseObservable() {
    return mStripeNum == DomainStripe.STRIPE_NUM_CURRENT ? mStore.currentStripe()
        : mStore.stripeWithNum(mStripeNum);
  }
}
