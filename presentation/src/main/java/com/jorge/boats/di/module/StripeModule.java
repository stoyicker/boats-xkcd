package com.jorge.boats.di.module;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.jorge.boats.di.PerActivity;
import com.jorge.boats.domain.interactor.UseCase;
import com.jorge.boats.io.task.TypefaceLoad;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;

@Module public class StripeModule {

  private static final String EMPTY_STRING = "";

  private final String mStripeId;

  public StripeModule(final String stripeId) {
    mStripeId = TextUtils.isEmpty(stripeId) ? EMPTY_STRING : stripeId;
  }

  @Provides @PerActivity @Named("typeface") UseCase provideTypefaceUseCase(
      final @NonNull TypefaceLoad typefaceLoad) {
    return typefaceLoad;
  }
}
