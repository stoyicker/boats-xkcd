package com.jorge.boats.di.module;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import com.jorge.boats.di.PerActivity;
import com.jorge.boats.domain.entity.DomainStripe;
import com.jorge.boats.domain.executor.PostExecutionThread;
import com.jorge.boats.domain.executor.ThreadExecutor;
import com.jorge.boats.domain.interactor.GetStripeUseCase;
import com.jorge.boats.domain.interactor.UseCase;
import com.jorge.boats.domain.repository.XkcdStore;
import com.jorge.boats.navigation.NavigationLayout;
import com.jorge.boats.task.TypefaceLoadTask;
import com.jorge.boats.view.widget.CustomTitleToolbar;
import com.jorge.boats.view.widget.RetryLayout;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;

@Module public class StripeModule {

  private final NavigationLayout mNavigationLayout;
  private final Toolbar mToolbar;
  private final RetryLayout mRetry;

  public StripeModule(final @NonNull NavigationLayout navigationLayout,
      final @NonNull Toolbar toolbar, final @NonNull RetryLayout errorView) {
    mNavigationLayout = navigationLayout;
    mToolbar = toolbar;
    mRetry = errorView;
  }

  @Provides @PerActivity CustomTitleToolbar provideCustomTitleToolbar() {
    return (CustomTitleToolbar) mToolbar;
  }

  @Provides @PerActivity NavigationLayout provideNavigationLayout() {
    return mNavigationLayout;
  }

  @Provides @PerActivity RetryLayout provideRetryView() {
    return mRetry;
  }

  @Provides @PerActivity @Named("typeface") UseCase<Typeface> provideTypefaceUseCase(
      final @NonNull TypefaceLoadTask typefaceLoad) {
    return typefaceLoad;
  }

  @Provides @PerActivity @Named("stripe") UseCase<DomainStripe> provideGetStripeUseCase(
      final @NonNull XkcdStore repository, final @NonNull ThreadExecutor threadExecutor,
      final @NonNull PostExecutionThread postExecutionThread) {
    return new GetStripeUseCase(repository, threadExecutor, postExecutionThread);
  }
}
