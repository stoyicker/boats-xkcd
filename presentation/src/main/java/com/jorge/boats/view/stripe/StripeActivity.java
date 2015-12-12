package com.jorge.boats.view.stripe;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.jorge.boats.R;
import com.jorge.boats.di.HasComponent;
import com.jorge.boats.di.component.ApplicationComponent;
import com.jorge.boats.di.component.DaggerStripeComponent;
import com.jorge.boats.di.component.StripeComponent;
import com.jorge.boats.di.module.StripeModule;
import com.jorge.boats.domain.entity.StripeEntity;
import com.jorge.boats.presenter.StripePresenter;
import com.jorge.boats.view.activity.BaseVisualActivity;
import javax.inject.Inject;

public class StripeActivity extends BaseVisualActivity
    implements StripeView, HasComponent<StripeComponent> {

  private static final String INTENT_EXTRA_PARAM_STRING_ID =
      StripeActivity.class.getName() + ".INTENT_PARAM_USER_ID";
  private static final String INSTANCE_STATE_PARAM_STRING_ID =
      StripeActivity.class.getName() + ".STATE_PARAM_USER_ID";

  private String mStripeId;
  private StripeComponent mStripeComponent;

  @Inject StripePresenter mStripePresenter;

  public static Intent getCallingIntent(final @NonNull Context context,
      final @Nullable String stripeId) {
    final Intent callingIntent = new Intent(context, StripeActivity.class);
    callingIntent.putExtra(INTENT_EXTRA_PARAM_STRING_ID, stripeId);

    return callingIntent;
  }

  @Override public void onCreate(final @Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    initializeActivity(savedInstanceState);
  }

  @Override protected ApplicationComponent createComponentAndInjectSelf() {
    this.mStripeComponent = DaggerStripeComponent.builder()
        .applicationComponent(getApplicationComponent())
        .stripeModule(new StripeModule(mStripeId))
        .build();
    mStripeComponent.inject(this);

    return mStripeComponent;
  }

  @Override protected void onSaveInstanceState(final @Nullable Bundle outState) {
    if (outState != null) {
      outState.putString(INSTANCE_STATE_PARAM_STRING_ID, this.mStripeId);
    }
    super.onSaveInstanceState(outState);
  }

  private void initializeActivity(final @Nullable Bundle savedInstanceState) {
    if (savedInstanceState == null) {
      this.mStripeId = getIntent().getStringExtra(INTENT_EXTRA_PARAM_STRING_ID);
      initializeStripePresenter();
    } else {
      this.mStripeId = savedInstanceState.getString(INSTANCE_STATE_PARAM_STRING_ID, null);
    }
  }

  private void initializeStripePresenter() {
    this.mStripePresenter.initializeView(this);
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

  @Override public void renderStripe(@NonNull StripeEntity model) {

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

  @Override public void setToolbarTitleTypeface(@NonNull Typeface typeface) {
    super.setToolbarTitleTypeface(typeface);
  }

  @Override public Context getContext() {
    return getApplicationContext();
  }

  @Override public StripeComponent getComponent() {
    return mStripeComponent;
  }
}
