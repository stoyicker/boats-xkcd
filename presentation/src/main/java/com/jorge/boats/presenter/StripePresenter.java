package com.jorge.boats.presenter;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import com.jorge.boats.di.PerActivity;
import com.jorge.boats.domain.interactor.UseCase;
import com.jorge.boats.view.LoadStripeView;
import javax.inject.Inject;
import javax.inject.Named;
import rx.Subscriber;
import timber.log.Timber;

@PerActivity public class StripePresenter implements Presenter {

  private final UseCase mTypefaceUseCase;
  private LoadStripeView mView;

  @Inject public StripePresenter(final @NonNull @Named("typeface") UseCase typefaceUseCase) {
    mTypefaceUseCase = typefaceUseCase;
  }

  public void initializeView(@NonNull LoadStripeView view) {
    this.mView = view;
    this.runSetupTasks();
  }

  private void runSetupTasks() {
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

  private final class TitleTypefaceSubscriber extends Subscriber<Typeface> {

    @Override public void onCompleted() {
      //Do nothing
    }

    @Override public void onError(final @NonNull Throwable e) {
      Timber.e(e, e.getClass().getName());
    }

    @Override public void onNext(final @NonNull Typeface typeface) {
      mView.setToolbarTitleTypeface(typeface);
    }
  }
}
