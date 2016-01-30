package com.jorge.boats.presenter;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import com.jorge.boats.di.PerActivity;
import com.jorge.boats.domain.entity.DomainStripe;
import com.jorge.boats.domain.interactor.GetStripeUseCase;
import com.jorge.boats.domain.interactor.UseCase;
import com.jorge.boats.log.ApplicationLogger;
import com.jorge.boats.mapper.PresentationEntityMapper;
import com.jorge.boats.view.stripe.StripeView;
import com.jorge.boats.view.widget.RetryLinearLayout;
import javax.inject.Inject;
import javax.inject.Named;
import retrofit2.HttpException;
import rx.Subscriber;

@PerActivity public class StripePresenter implements Presenter<StripeView> {

  private final UseCase<Typeface> mTypefaceUseCase;
  private final UseCase<DomainStripe> mStripeUseCase;

  @Inject PresentationEntityMapper mEntityMapper;
  @Inject RetryLinearLayout mRetry;

  private StripeView mView;

  @Inject
  public StripePresenter(final @NonNull @Named("typeface") UseCase<Typeface> typefaceUseCase,
      final @NonNull @Named("stripe") UseCase<DomainStripe> stripeUseCase) {
    mTypefaceUseCase = typefaceUseCase;
    mStripeUseCase = stripeUseCase;
  }

  public void setView(@NonNull StripeView view) {
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
    ((GetStripeUseCase) mStripeUseCase).setRequestedStripeNum(stripeNum);
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

  @Override public void requestRetry() {
    requestStripe();
  }

  @Override public boolean isRetryViewShown() {
    return mRetry.isShown();
  }

  public void actionNext() {
    //There is no feasible way to know if this is the latest stripe without first trying

    switchToStripeNum(mView.getStripeNum() + 1);
  }

  public void actionShare() {
    mView.share();
  }

  public void actionPrevious() {
    final long currentlyShownStripeNum = mView.getStripeNum();

    if (currentlyShownStripeNum == DomainStripe.STRIPE_NUM_FIRST) {
      return;
    }

    switchToStripeNum(mView.getStripeNum() - 1);
  }

  private final class TitleTypefaceSubscriber extends Subscriber<Typeface> {

    @Override public void onCompleted() {
      //Do nothing
    }

    @Override public void onError(final @NonNull Throwable e) {
      ApplicationLogger.e(e, e.getClass().getName());
    }

    @Override public void onNext(final @NonNull Typeface typeface) {
      mView.setTitleTypeface(typeface);
      mRetry.setTextTypeface(typeface);
    }
  }

  private final class StripeSubscriber extends Subscriber<DomainStripe> {

    @Override public void onStart() {
      mView.hideRetry();
      mView.showLoading();
    }

    @Override public void onCompleted() {
      mView.hideLoading();
    }

    @Override public void onError(final @NonNull Throwable e) {
      ApplicationLogger.e(e, e.getClass().getName());
      mView.hideLoading();
      //Upon attempt of loading a non-existing stripe, do nothing instead
      if (!(e instanceof HttpException) || ((HttpException) e).code() != 404) {
        mView.showError(e);
        mView.showRetry();
      }
    }

    @Override public void onNext(final @NonNull DomainStripe domainStripe) {
      mView.setStripeNum(domainStripe.getNum());
      mView.renderStripe(mEntityMapper.transform(domainStripe));
    }
  }
}
