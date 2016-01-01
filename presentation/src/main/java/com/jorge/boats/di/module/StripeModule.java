package com.jorge.boats.di.module;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import com.jorge.boats.di.PerActivity;
import com.jorge.boats.domain.entity.DomainStripe;
import com.jorge.boats.domain.executor.PostExecutionThread;
import com.jorge.boats.domain.executor.ThreadExecutor;
import com.jorge.boats.domain.interactor.GetStripeUseCase;
import com.jorge.boats.domain.interactor.UseCase;
import com.jorge.boats.domain.repository.XkcdStore;
import com.jorge.boats.task.TypefaceLoadTask;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;

@Module public class StripeModule {

  private final long mStripeNum;

  public StripeModule(final long stripeNum) {
    mStripeNum = stripeNum;
  }

  @Provides @PerActivity @Named("typeface") UseCase<Typeface> provideTypefaceUseCase(
      final @NonNull TypefaceLoadTask typefaceLoad) {
    return typefaceLoad;
  }

  @Provides @PerActivity @Named("stripe") UseCase<DomainStripe> provideGetStripeUseCase(
      final @NonNull XkcdStore repository, final @NonNull ThreadExecutor threadExecutor,
      final @NonNull PostExecutionThread postExecutionThread) {
    return new GetStripeUseCase(mStripeNum, repository, threadExecutor, postExecutionThread);
  }
}
