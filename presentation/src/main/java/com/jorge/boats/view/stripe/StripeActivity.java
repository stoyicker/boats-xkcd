package com.jorge.boats.view.stripe;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import com.jorge.boats.R;
import com.jorge.boats.model.StripeModel;
import com.jorge.boats.presenter.StripePresenter;
import com.jorge.boats.view.activity.BaseVisualActivity;
import javax.inject.Inject;

public class StripeActivity extends BaseVisualActivity implements StripeView {

  @Inject StripePresenter mStripePresenter;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    initialize();
    initializeStripePresenter();
  }

  private void initialize() {
    this.getApplicationComponent().inject(this); //TODO Find the alternative for activities
    this.mStripePresenter.setView(this);
  }

  private void initializeStripePresenter() {
    mStripePresenter.initialize();
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

  @Override public void renderStripe(@NonNull StripeModel user) {

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
