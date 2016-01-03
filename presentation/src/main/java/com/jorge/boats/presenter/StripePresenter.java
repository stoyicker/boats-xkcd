package com.jorge.boats.presenter;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import com.fernandocejas.frodo.annotation.RxLogSubscriber;
import com.jorge.boats.di.PerActivity;
import com.jorge.boats.domain.entity.DomainStripe;
import com.jorge.boats.domain.interactor.GetStripeUseCase;
import com.jorge.boats.domain.interactor.UseCase;
import com.jorge.boats.log.ApplicationLogger;
import com.jorge.boats.mapper.PresentationEntityMapper;
import com.jorge.boats.view.stripe.StripeView;
import javax.inject.Inject;
import javax.inject.Named;
import rx.Subscriber;

@PerActivity public class StripePresenter implements Presenter<StripeView> {

  private long mStripeNum;

  private final UseCase<Typeface> mTypefaceUseCase;
  private final UseCase<DomainStripe> mStripeUseCase;

  private final PresentationEntityMapper mEntityMapper;
  private StripeView mView;

  @Inject
  public StripePresenter(final @NonNull @Named("typeface") UseCase<Typeface> typefaceUseCase,
      final @NonNull @Named("stripe") UseCase<DomainStripe> stripeUseCase,
      PresentationEntityMapper presentationEntityMapper) {
    mTypefaceUseCase = typefaceUseCase;
    mStripeUseCase = stripeUseCase;
    mEntityMapper = presentationEntityMapper;
  }

  public void createWithView(@NonNull StripeView view) {
    initialize();
    mView = view;
  }

  public void initialize() {
    requestTitleTypeface();
  }

  private void requestTitleTypeface() {
    mTypefaceUseCase.execute(new TitleTypefaceSubscriber());
  }

  private void requestStripe() {
    loadStripe();
  }

  private void loadStripe() {
    mStripeUseCase.execute(new StripeSubscriber());
  }

  public void switchToStripeNum(final long stripeNum) {
    if (stripeNum < DomainStripe.STRIPE_NUM_CURRENT) {
      throw new IllegalArgumentException("Illegal stripe num " + stripeNum + ".");
    }
    mStripeNum = stripeNum;
    ((GetStripeUseCase) mStripeUseCase).setRequestedStripeNum(mStripeNum);
    requestStripe();
  }

  @Override public void resume() {
  }

  @Override public void pause() {

  }

  @Override public void destroy() {
    mTypefaceUseCase.destroy();
    mStripeUseCase.destroy();
  }

  @RxLogSubscriber private final class TitleTypefaceSubscriber extends Subscriber<Typeface> {

    @Override public void onCompleted() {
      //Do nothing
    }

    @Override public void onError(final @NonNull Throwable e) {
      ApplicationLogger.e(e, e.getClass().getName());
    }

    @Override public void onNext(final @NonNull Typeface typeface) {
      mView.setTitleTypeface(typeface);
    }
  }

  private final class StripeSubscriber extends Subscriber<DomainStripe> {

    @Override public void onStart() {
      mView.showLoading();
      mView.hideRetry();
    }

    @Override public void onCompleted() {
      mView.hideLoading();
    }

    @Override public void onError(final @NonNull Throwable e) {
      ApplicationLogger.e(e, e.getClass().getName());
      mView.showError(e);
      mView.showRetry();
    }

    @Override public void onNext(final @NonNull DomainStripe domainStripe) {
      mView.renderStripe(mEntityMapper.transform(domainStripe));
    }
  }
}
