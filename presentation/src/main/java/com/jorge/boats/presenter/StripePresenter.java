package com.jorge.boats.presenter;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import com.jorge.boats.domain.interactor.UseCase;
import com.jorge.boats.view.LoadStripeView;
import javax.inject.Inject;
import javax.inject.Named;
import rx.Subscriber;

public class StripePresenter implements Presenter {

  private final UseCase mTypefaceUseCase;
  private LoadStripeView mView;

  @Inject public StripePresenter(final @NonNull @Named("typeface") UseCase typefaceUseCase) {
    mTypefaceUseCase = typefaceUseCase;
  }

  public void setView(@NonNull LoadStripeView view) {
    this.mView = view;
  }

  public void initialize() {
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

  private static final class TitleTypefaceSubscriber extends Subscriber<Typeface> {

    @Override public void onCompleted() {
      //TODO On completed for typeface load
    }

    @Override public void onError(final @NonNull Throwable e) {
      //TODO On error for typeface load
    }

    @Override public void onNext(final @NonNull Typeface typeface) {
      //TODO On next for typeface load
    }
  }
}
