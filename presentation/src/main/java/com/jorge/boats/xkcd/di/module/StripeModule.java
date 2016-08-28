package com.jorge.boats.xkcd.di.module;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;

import com.jorge.boats.xkcd.di.PerActivity;
import com.jorge.boats.xkcd.domain.entity.DomainStripe;
import com.jorge.boats.xkcd.domain.executor.PostExecutionThread;
import com.jorge.boats.xkcd.domain.executor.ThreadExecutor;
import com.jorge.boats.xkcd.domain.interactor.GetStripeUseCase;
import com.jorge.boats.xkcd.domain.interactor.SingleUseCase;
import com.jorge.boats.xkcd.domain.interactor.UseCase;
import com.jorge.boats.xkcd.domain.repository.XkcdStore;
import com.jorge.boats.xkcd.navigation.NavigationLinearLayout;
import com.jorge.boats.xkcd.view.task.TypefaceLoadTask;
import com.jorge.boats.xkcd.view.widget.CustomTitleToolbar;
import com.jorge.boats.xkcd.view.widget.RetryLinearLayout;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class StripeModule {

  private final NavigationLinearLayout mNavigationLayout;
  private final Toolbar mToolbar;
  private final RetryLinearLayout mRetry;

  public StripeModule(final @NonNull NavigationLinearLayout navigationLayout,
      final @NonNull Toolbar toolbar, final @NonNull RetryLinearLayout errorView) {
    mNavigationLayout = navigationLayout;
    mToolbar = toolbar;
    mRetry = errorView;
  }

  @Provides
  @PerActivity
  CustomTitleToolbar provideCustomTitleToolbar() {
    return (CustomTitleToolbar) mToolbar;
  }

  @Provides
  @PerActivity
  NavigationLinearLayout provideNavigationLayout() {
    return mNavigationLayout;
  }

  @Provides
  @PerActivity
  RetryLinearLayout provideRetryView() {
    return mRetry;
  }

  @Provides
  @PerActivity
  @Named("typeface")
  SingleUseCase<Typeface> provideTypefaceUseCase(
      final @NonNull TypefaceLoadTask typefaceLoad) {
    return typefaceLoad;
  }

  @Provides
  @PerActivity
  @Named("stripe")
  UseCase<DomainStripe> provideGetStripeUseCase(
      final @NonNull XkcdStore repository, final @NonNull ThreadExecutor threadExecutor,
      final @NonNull PostExecutionThread postExecutionThread) {
    return new GetStripeUseCase(repository, threadExecutor, postExecutionThread);
  }
}
