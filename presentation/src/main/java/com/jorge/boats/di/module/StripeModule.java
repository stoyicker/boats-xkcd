package com.jorge.boats.di.module;

import android.support.annotation.NonNull;
import com.jorge.boats.di.PerActivity;
import com.jorge.boats.domain.executor.PostExecutionThread;
import com.jorge.boats.domain.executor.ThreadExecutor;
import com.jorge.boats.domain.interactor.GetStripeUseCase;
import com.jorge.boats.domain.interactor.UseCase;
import com.jorge.boats.domain.repository.XkcdRepository;
import com.jorge.boats.task.TypefaceLoadTask;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;

@Module public class StripeModule {

  private final long mStripeId;

  public StripeModule(final long stripeId) {
    mStripeId = stripeId;
  }

  @Provides @PerActivity @Named("typeface") UseCase provideTypefaceUseCase(
      final @NonNull TypefaceLoadTask typefaceLoad) {
    return typefaceLoad;
  }

  @Provides @PerActivity @Named("getStripe") UseCase provideGetStripeUseCase(
      final @NonNull XkcdRepository repository, final @NonNull ThreadExecutor threadExecutor,
      final @NonNull PostExecutionThread postExecutionThread) {
    return new GetStripeUseCase(mStripeId, repository, threadExecutor, postExecutionThread);
  }
}
