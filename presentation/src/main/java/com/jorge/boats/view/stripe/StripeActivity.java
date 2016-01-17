package com.jorge.boats.view.stripe;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import com.jorge.boats.R;
import com.jorge.boats.di.component.DaggerStripeComponent;
import com.jorge.boats.di.module.StripeModule;
import com.jorge.boats.domain.entity.DomainStripe;
import com.jorge.boats.entity.PresentationStripe;
import com.jorge.boats.navigation.NavigationLayoutGestureDetector;
import com.jorge.boats.presenter.StripePresenter;
import com.jorge.boats.view.activity.BaseBrowsableActivity;
import javax.inject.Inject;

public class StripeActivity extends BaseBrowsableActivity implements StripeView {

  private static final String INTENT_EXTRA_PARAM_STRIPE_NUM =
      StripeActivity.class.getName() + ".INTENT_PARAM_STRIPE_NUM";
  private static final String INSTANCE_STATE_PARAM_STRIPE_NUM =
      StripeActivity.class.getName() + ".STATE_PARAM_STRIPE_NUM";

  private long mStripeNum;

  @Inject NavigationLayoutGestureDetector mNavigationLayoutGestureDetector;
  @Inject StripePresenter mStripePresenter;

  @NonNull
  public static Intent getCallingIntent(final @NonNull Context context, final long stripeNum) {
    Intent callingIntent = new Intent(context, StripeActivity.class);
    callingIntent.putExtra(INTENT_EXTRA_PARAM_STRIPE_NUM, stripeNum);

    return callingIntent;
  }

  @Override public void onCreate(final @Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    initializeActivity(savedInstanceState);
    initializeStripePresenter();
  }

  @Override public boolean onTouchEvent(final @NonNull MotionEvent event) {
    return this.mNavigationLayoutGestureDetector.onTouchEvent(event) || super.onTouchEvent(event);
  }

  @Override protected void createComponentAndInjectSelf() {
    DaggerStripeComponent.builder()
        .applicationComponent(getApplicationComponent())
        .stripeModule(new StripeModule(super.getNavigationLayout()))
        .build()
        .inject(this);
  }

  @Override protected void onSaveInstanceState(final @Nullable Bundle outState) {
    if (outState != null) {
      outState.putLong(INSTANCE_STATE_PARAM_STRIPE_NUM, this.mStripeNum);
    }
    super.onSaveInstanceState(outState);
  }

  @Override protected void onRestoreInstanceState(final @Nullable Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    initializeActivity(savedInstanceState);
  }

  private void initializeActivity(final @Nullable Bundle savedInstanceState) {
    if (savedInstanceState == null) {
      this.mStripeNum =
          getIntent().getLongExtra(INTENT_EXTRA_PARAM_STRIPE_NUM, DomainStripe.STRIPE_NUM_CURRENT);
    } else {
      this.mStripeNum = savedInstanceState.getLong(INSTANCE_STATE_PARAM_STRIPE_NUM,
          DomainStripe.STRIPE_NUM_CURRENT);
    }
  }

  @Override public void setStripeNum(final long stripeNum) {
    mStripeNum = stripeNum;
  }

  private void initializeStripePresenter() {
    this.mStripePresenter.setView(this);
    this.mStripePresenter.initialize();
    this.mStripePresenter.switchToStripeNum(mStripeNum);
  }

  @Override public void onResume() {
    super.onResume();
    this.mStripePresenter.resume();
  }

  @Override public void onPause() {
    super.onPause();
    this.mStripePresenter.pause();
  }

  @Override public void onDestroy() {
    super.onDestroy();
    this.mStripePresenter.destroy();
  }

  @Override public void setTitleTypeface(@NonNull Typeface titleTypeface) {
    super.getToolbar().getTitleView().setTypeface(titleTypeface);
  }

  @Override public void renderStripe(@NonNull PresentationStripe model) {
    super.getToolbar().setTitle(model.getTitle());
    //TODO Rest of render stripe
  }

  @Override public void showLoading() {
    super.getLoadingView().setVisibility(View.VISIBLE);
  }

  @Override public void hideLoading() {
    super.getLoadingView().setVisibility(View.GONE);
  }

  @Override public void showRetry() {

  }

  @Override public void hideRetry() {

  }

  @Override public void showError(final @NonNull Throwable throwable) {
    super.getToolbar().setTitle(getString(R.string.error_title));
    Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_LONG).show();
  }

  @Override public Context getContext() {
    return getApplicationContext();
  }
}
