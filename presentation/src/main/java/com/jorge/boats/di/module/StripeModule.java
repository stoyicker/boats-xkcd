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
import com.jorge.boats.navigation.NavigationLinearLayout;
import com.jorge.boats.task.TypefaceLoadTask;
import com.jorge.boats.view.widget.CustomTitleToolbar;
import com.jorge.boats.view.widget.RetryLinearLayout;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;

@Module public class StripeModule {

  private final NavigationLinearLayout mNavigationLayout;
  private final Toolbar mToolbar;
  private final RetryLinearLayout mRetry;

  public StripeModule(final @NonNull NavigationLinearLayout navigationLayout,
      final @NonNull Toolbar toolbar, final @NonNull RetryLinearLayout errorView) {
    mNavigationLayout = navigationLayout;
    mToolbar = toolbar;
    mRetry = errorView;
  }

  @Provides @PerActivity CustomTitleToolbar provideCustomTitleToolbar() {
    return (CustomTitleToolbar) mToolbar;
  }

  @Provides @PerActivity NavigationLinearLayout provideNavigationLayout() {
    return mNavigationLayout;
  }

  @Provides @PerActivity RetryLinearLayout provideRetryView() {
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
