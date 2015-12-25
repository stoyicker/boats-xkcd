package com.jorge.boats.presenter;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import com.fernandocejas.frodo.annotation.RxLogSubscriber;
import com.jorge.boats.di.PerActivity;
import com.jorge.boats.domain.entity.DomainStripe;
import com.jorge.boats.domain.interactor.UseCase;
import com.jorge.boats.view.stripe.StripeView;
import javax.inject.Inject;
import javax.inject.Named;
import rx.Subscriber;
import timber.log.Timber;

@PerActivity public class StripePresenter implements Presenter {

  private long mStripeNum;

  private final UseCase<Typeface> mTypefaceUseCase;
  private StripeView mView;

  @Inject
  public StripePresenter(final @NonNull @Named("typeface") UseCase<Typeface> typefaceUseCase) {
    mTypefaceUseCase = typefaceUseCase;
  }

  public void setView(@NonNull StripeView view) {
    this.mView = view;
  }

  public void initialize(final long stripeNum) {
    if (stripeNum < DomainStripe.STRIPE_NUM_CURRENT) {
      throw new IllegalArgumentException("Illegal stripe num " + stripeNum + ".");
    }
    mStripeNum = stripeNum;
    this.loadTitleTypeface();
  }

  private void loadTitleTypeface() {
    mTypefaceUseCase.execute(new TitleTypefaceSubscriber());
  }

  @Override public void resume() {

  }

  @Override public void pause() {

  }

  @Override public void destroy() {
    mTypefaceUseCase.unsubscribe();
  }

  @RxLogSubscriber private final class TitleTypefaceSubscriber extends Subscriber<Typeface> {

    @Override public void onCompleted() {
      //Do nothing
    }

    @Override public void onError(final @NonNull Throwable e) {
      Timber.e(e, e.getClass().getName());
    }

    @Override public void onNext(final @NonNull Typeface typeface) {
      mView.setTitleTypeface(typeface);
    }
  }
}
