package com.jorge.boats.xkcd.presenter;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import com.jorge.boats.xkcd.data.P;
import com.jorge.boats.xkcd.di.PerActivity;
import com.jorge.boats.xkcd.domain.entity.DomainStripe;
import com.jorge.boats.xkcd.domain.interactor.GetStripeUseCase;
import com.jorge.boats.xkcd.domain.interactor.UseCase;
import com.jorge.boats.xkcd.log.ApplicationLogger;
import com.jorge.boats.xkcd.mapper.PresentationEntityMapper;
import com.jorge.boats.xkcd.util.RandomUtil;
import com.jorge.boats.xkcd.view.stripe.StripeContentView;
import com.jorge.boats.xkcd.view.widget.RetryLinearLayout;
import javax.inject.Inject;
import javax.inject.Named;
import retrofit2.HttpException;
import rx.Subscriber;

@PerActivity public class StripePresenter implements Presenter<StripeContentView> {

  private final UseCase<Typeface> mTypefaceUseCase;
  private final UseCase<DomainStripe> mStripeUseCase;

  @Inject PresentationEntityMapper mEntityMapper;
  @Inject RetryLinearLayout mRetry;

  private StripeContentView mView;

  @Inject
  public StripePresenter(final @NonNull @Named("typeface") UseCase<Typeface> typefaceUseCase,
      final @NonNull @Named("stripe") UseCase<DomainStripe> stripeUseCase) {
    mTypefaceUseCase = typefaceUseCase;
    mStripeUseCase = stripeUseCase;
  }

  public void setView(@NonNull StripeContentView view) {
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
    updateUseCase(stripeNum);
    requestStripe();
  }

  private void updateUseCase(final long stripeNum) {
    ((GetStripeUseCase) mStripeUseCase).setRequestedStripeNum(stripeNum);
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
    if (isRetryViewShown()) return;

    switchToStripeNum(mView.getStripeNum() + 1);
  }

  public void actionRandom() {
    if (isRetryViewShown()) return;
    switchToStripeNum(RandomUtil.nextInt(1, (int) (mView.getStripeNum() - 1)));
  }

  public void actionShare() {
    mView.share();
  }

  public void actionPrevious() {
    if (isRetryViewShown()) return;
    long targetContainer = mView.getStripeNum();

    if (targetContainer == DomainStripe.STRIPE_NUM_FIRST) {
      return;
    }

    switchToStripeNum(targetContainer - 1);
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
      mView.showLoading();
      mView.hideRetry();
    }

    @Override public void onCompleted() {
      mView.showContent();
      mView.hideLoading();
      mView.hideRetry();
    }

    @Override public void onError(final @NonNull Throwable e) {
      ApplicationLogger.e(e, e.getClass().getName());
      mView.hideLoading();
      //Upon attempt of loading a non-existing stripe, load the current stripe instead
      if ((e instanceof HttpException) && ((HttpException) e).code() == 404) {
        switchToStripeNum(DomainStripe.STRIPE_NUM_CURRENT);
        return;
      }
      mView.showRetry(e);
      mView.hideContent();
    }

    @Override public void onNext(final @NonNull DomainStripe domainStripe) {
      final long num;

      mView.setStripeNum(num = domainStripe.getNum());
      updateUseCase(num);
      P.lastShownStripeNum.put((int) num).apply();
      mView.renderStripe(mEntityMapper.transform(domainStripe));
    }
  }
}
