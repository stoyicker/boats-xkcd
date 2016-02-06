package com.jorge.boats.xkcd.view.stripe;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import butterknife.Bind;
import com.bumptech.glide.Glide;
import com.jorge.boats.xkcd.BuildConfig;
import com.jorge.boats.xkcd.R;
import com.jorge.boats.xkcd.di.component.DaggerStripeComponent;
import com.jorge.boats.xkcd.di.module.StripeModule;
import com.jorge.boats.xkcd.domain.entity.DomainStripe;
import com.jorge.boats.xkcd.entity.PresentationStripe;
import com.jorge.boats.xkcd.navigation.NavigationLayoutGestureDetector;
import com.jorge.boats.xkcd.navigation.NavigationLinearLayout;
import com.jorge.boats.xkcd.presenter.StripePresenter;
import com.jorge.boats.xkcd.util.ResourceUtil;
import com.jorge.boats.xkcd.util.ViewServerDelegate;
import com.jorge.boats.xkcd.view.activity.BaseVisualActivity;
import com.jorge.boats.xkcd.view.settings.SettingsActivity;
import com.jorge.boats.xkcd.view.widget.CustomTitleToolbar;
import com.jorge.boats.xkcd.view.widget.RetryLinearLayout;
import javax.inject.Inject;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class StripeActivity extends BaseVisualActivity implements StripeContentView {

  private static final String INTENT_EXTRA_PARAM_STRIPE_NUM =
      StripeActivity.class.getName() + ".INTENT_PARAM_STRIPE_NUM";
  private static final String INSTANCE_STATE_PARAM_STRIPE_NUM =
      StripeActivity.class.getName() + ".STATE_PARAM_STRIPE_NUM";

  private final CharSequence[] mShareableRenderedData = new CharSequence[2]; //Title and link
  private PhotoViewAttacher mAttacher;

  private long mStripeNum;

  @Inject NavigationLayoutGestureDetector mGestureDetector;
  @Inject StripePresenter mStripePresenter;

  @Bind(R.id.content) View mContent;
  @Bind(R.id.toolbar) CustomTitleToolbar mToolbar;
  @Bind(R.id.navigation) NavigationLinearLayout mNavigation;
  @Bind(R.id.progress_bar) View mLoading;
  @Bind(R.id.retry) RetryLinearLayout mRetry;
  @Bind(R.id.image) PhotoView mImage;

  @NonNull
  public static Intent getCallingIntent(final @NonNull Context context, final long stripeNum) {
    Intent callingIntent = new Intent(context, StripeActivity.class);
    callingIntent.putExtra(INTENT_EXTRA_PARAM_STRIPE_NUM, stripeNum);

    return callingIntent;
  }

  @Override public void onCreate(final @Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    createComponentAndInjectSelf();

    setupToolbar();
    setupNavigationLayout();
    setupLoading();
    initializeActivity(savedInstanceState);
    initializeStripePresenter();
    initializeNavigation();
    initializeRetry();
    initializeImage();

    ViewServerDelegate.addWindow(this);
  }

  private void setupToolbar() {
    setSupportActionBar(mToolbar);
  }

  private void setupNavigationLayout() {
    final RelativeLayout.LayoutParams navigationLayoutLp =
        (RelativeLayout.LayoutParams) mNavigation.getLayoutParams();
    final boolean isLandscape =
        getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

    if (isLandscape) {
      navigationLayoutLp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
    } else {
      navigationLayoutLp.addRule(RelativeLayout.BELOW, mLoading.getId());
      navigationLayoutLp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
    }

    mNavigation.setLayoutParams(navigationLayoutLp);
  }

  private void setupLoading() {
    final RelativeLayout.LayoutParams progressBarLayoutParams =
        (RelativeLayout.LayoutParams) mLoading.getLayoutParams();

    progressBarLayoutParams.addRule(RelativeLayout.BELOW, R.id.ad_banner);

    mLoading.setLayoutParams(progressBarLayoutParams);
  }

  @Override public boolean onTouchEvent(final @NonNull MotionEvent event) {
    return this.mGestureDetector.onTouchEvent(event) || super.onTouchEvent(event);
  }

  @Override public boolean onCreateOptionsMenu(final @NonNull Menu menu) {
    final MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.activity_stripe, menu);

    return super.onCreateOptionsMenu(menu);
  }

  @Override public boolean onOptionsItemSelected(final @NonNull MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_settings:
        openSettings();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  private void openSettings() {
    final Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      //noinspection unchecked
      startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    } else {
      startActivity(intent);
    }
  }

  @Override protected void createComponentAndInjectSelf() {
    DaggerStripeComponent.builder()
        .applicationComponent(getApplicationComponent())
        .stripeModule(new StripeModule(mNavigation, mToolbar, mRetry))
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

  @Override public long getStripeNum() {
    return mStripeNum;
  }

  private void initializeStripePresenter() {
    this.mStripePresenter.setView(this);
    this.mStripePresenter.initialize();
    this.mStripePresenter.switchToStripeNum(mStripeNum);
  }

  private void initializeNavigation() {
    this.mNavigation.setStripePresenter(this.mStripePresenter);
  }

  private void initializeRetry() {
    this.mRetry.setStripePresenter(this.mStripePresenter);
    this.mRetry.setIntermediateGestureDetector(this.mGestureDetector);
  }

  private void initializeImage() {
    this.mAttacher = new PhotoViewAttacher(this.mImage, true, BuildConfig.DEBUG);
    this.mAttacher.setIntermediateGestureDetector(this.mGestureDetector);
  }

  @Override public void onResume() {
    super.onResume();
    this.mStripePresenter.resume();
    ViewServerDelegate.setFocusedWindow(this);
  }

  @Override public void onPause() {
    super.onPause();
    this.mStripePresenter.pause();
  }

  @Override public void onDestroy() {
    super.onDestroy();
    this.mStripePresenter.destroy();
    ViewServerDelegate.removeWindow(this);
  }

  @Override public void setTitleTypeface(final @NonNull Typeface titleTypeface) {
    mToolbar.getTitleView().setTypeface(titleTypeface);
  }

  @Override public void renderStripe(final @NonNull PresentationStripe model) {
    final CharSequence title;

    mToolbar.setTitle(title = model.getTitle());
    Glide.with(this)
        .load(model.getImg())
        .crossFade(getResources().getInteger(R.integer.content_in_duration_milliseconds))
        .into(mImage);
    mImage.setContentDescription(title);
    mAttacher.update();
    this.mAttacher.setMinimumScale(this.mImage.getScale());

    updateShareableData(model);
  }

  private void updateShareableData(final @NonNull PresentationStripe model) {
    mShareableRenderedData[0] = model.getTitle();
    mShareableRenderedData[1] = model.getImg();
  }

  @Override public void showContent() {
    mImage.setBackgroundColor(
        ResourceUtil.getColor(this, R.color.content_background_normal, getTheme()));
    mImage.setVisibility(View.VISIBLE);
  }

  @Override public void hideContent() {
    mImage.setVisibility(View.INVISIBLE);
  }

  @Override public void showLoading() {
    if (!mRetry.isShown()) {
      mContent.setBackgroundColor(
          ResourceUtil.getColor(this, R.color.content_background_normal, getTheme()));
    }
    mLoading.setVisibility(View.VISIBLE);
  }

  @Override public void hideLoading() {
    mLoading.setVisibility(View.INVISIBLE);
  }

  @Override public void showRetry(final @NonNull Throwable throwable) {
    mNavigation.hide();
    mToolbar.setTitle(getString(R.string.content_error_title));
    mContent.setBackgroundColor(
        ResourceUtil.getColor(this, R.color.content_background_empty, getTheme()));
    mRetry.setVisibility(View.VISIBLE);
  }

  @Override public void hideRetry() {
    mRetry.setVisibility(View.INVISIBLE);
  }

  @Override public Context getContext() {
    return getApplicationContext();
  }

  @SuppressLint("InlinedApi") @Override public void share() {
    //Prevent trying to share "nothing"
    for (final CharSequence x : mShareableRenderedData) {
      if (TextUtils.isEmpty(x)) return;
    }

    final Intent intent = new Intent(android.content.Intent.ACTION_SEND);

    intent.setType("text/plain");
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
    intent.putExtra(Intent.EXTRA_SUBJECT, mShareableRenderedData[0]);
    intent.putExtra(Intent.EXTRA_TEXT, mShareableRenderedData[1]);

    startActivity(Intent.createChooser(intent, getString(R.string.action_share_title)));
  }
}
