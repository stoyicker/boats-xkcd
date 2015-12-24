package com.jorge.boats.domain.interactor;

import com.jorge.boats.domain.entity.DomainStripe;
import com.jorge.boats.domain.executor.PostExecutionThread;
import com.jorge.boats.domain.executor.ThreadExecutor;
import com.jorge.boats.domain.repository.XkcdRepository;
import javax.inject.Inject;
import rx.Observable;

public class GetStripeUseCase extends UseCase<DomainStripe> {

  private final long mStripeId;
  private final XkcdRepository mRepository;

  @Inject public GetStripeUseCase(final long stripeId, final XkcdRepository repository,
      final ThreadExecutor threadExecutor, final PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    mStripeId = stripeId;
    mRepository = repository;
  }

  @Override protected Observable<DomainStripe> buildUseCaseObservable() {
    return mStripeId == DomainStripe.STRIPE_ID_CURRENT ? mRepository.currentStripe()
        : mRepository.stripeWithId(mStripeId);
  }
}
