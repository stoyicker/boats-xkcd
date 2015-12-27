package com.jorge.boats.view.stripe;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;
import com.jorge.boats.R;
import com.jorge.boats.di.component.DaggerStripeComponent;
import com.jorge.boats.di.component.StripeComponent;
import com.jorge.boats.di.module.StripeModule;
import com.jorge.boats.domain.entity.DomainStripe;
import com.jorge.boats.presenter.StripePresenter;
import com.jorge.boats.view.activity.BaseVisualActivity;
import javax.inject.Inject;

public class StripeActivity extends BaseVisualActivity implements StripeView {

  private static final String INTENT_EXTRA_PARAM_STRING_ID =
      StripeActivity.class.getName() + ".INTENT_PARAM_USER_ID";
  private static final String INSTANCE_STATE_PARAM_STRING_ID =
      StripeActivity.class.getName() + ".STATE_PARAM_USER_ID";

  private long mStripeNum;
  private StripeComponent mStripeComponent;

  @Inject StripePresenter mStripePresenter;

  @Override public void onCreate(final @Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    initializeActivity(savedInstanceState);
  }

  @Override protected void createComponentAndInjectSelf() {
    this.mStripeComponent = DaggerStripeComponent.builder()
        .applicationComponent(getApplicationComponent())
        .stripeModule(new StripeModule(mStripeNum))
        .build();
    mStripeComponent.inject(this);
  }

  @Override protected void onSaveInstanceState(final @Nullable Bundle outState) {
    if (outState != null) {
      outState.putLong(INSTANCE_STATE_PARAM_STRING_ID, this.mStripeNum);
    }
    super.onSaveInstanceState(outState);
  }

  private void initializeActivity(final @Nullable Bundle savedInstanceState) {
    if (savedInstanceState == null) {
      this.mStripeNum =
          getIntent().getLongExtra(INTENT_EXTRA_PARAM_STRING_ID, DomainStripe.STRIPE_NUM_CURRENT);
    } else {
      this.mStripeNum = savedInstanceState.getLong(INSTANCE_STATE_PARAM_STRING_ID,
          DomainStripe.STRIPE_NUM_CURRENT);
    }
    initializeStripePresenter();
  }

  private void initializeStripePresenter() {
    this.mStripePresenter.setView(this);
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

  @Override public void renderStripe(@NonNull DomainStripe model) {

  }

  @Override public void setTitleTypeface(@NonNull Typeface titleTypeface) {
    super.getTitleView().setTypeface(titleTypeface);
  }

  @Override public TextView getTitleView() {
    return super.getTitleView();
  }

  @Override public void showLoading() {

  }

  @Override public void hideLoading() {

  }

  @Override public void showRetry() {

  }

  @Override public void hideRetry() {

  }

  @Override public void showError(@LoadStripeError int errorCode) {

  }

  @Override public Context getContext() {
    return getApplicationContext();
  }
}
