package com.jorge.boats.xkcd.view.stripe;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.jorge.boats.xkcd.BuildConfig;
import com.jorge.boats.xkcd.MainApplication;
import com.jorge.boats.xkcd.R;
import com.jorge.boats.xkcd.data.P;
import com.jorge.boats.xkcd.di.component.DaggerStripeComponent;
import com.jorge.boats.xkcd.di.module.StripeModule;
import com.jorge.boats.xkcd.domain.entity.DomainStripe;
import com.jorge.boats.xkcd.entity.PresentationStripe;
import com.jorge.boats.xkcd.navigation.NavigationGestureDetector;
import com.jorge.boats.xkcd.navigation.NavigationLinearLayout;
import com.jorge.boats.xkcd.presenter.StripePresenter;
import com.jorge.boats.xkcd.task.BackgroundTaskManager;
import com.jorge.boats.xkcd.util.ActivityUtil;
import com.jorge.boats.xkcd.util.GooglePlayUtil;
import com.jorge.boats.xkcd.util.ResourceUtil;
import com.jorge.boats.xkcd.util.ThemeUtil;
import com.jorge.boats.xkcd.view.BaseView;
import com.jorge.boats.xkcd.view.activity.ViewServerAppCompatActivity;
import com.jorge.boats.xkcd.view.animation.BoatsLayoutTransition;
import com.jorge.boats.xkcd.view.settings.SettingsActivity;
import com.jorge.boats.xkcd.view.widget.CustomTitleToolbar;
import com.jorge.boats.xkcd.view.widget.PhotoViewExceptionProofRelativeLayout;
import com.jorge.boats.xkcd.view.widget.RetryLinearLayout;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class StripeActivity extends ViewServerAppCompatActivity
    implements BaseView, StripeContentView {

  public static final String INTENT_EXTRA_PARAM_STRIPE_NUM =
      StripeActivity.class.getName() + ".INTENT_PARAM_STRIPE_NUM";
  private static final String INSTANCE_STATE_PARAM_STRIPE_NUM =
      StripeActivity.class.getName() + ".STATE_PARAM_STRIPE_NUM";

  /**
   * Title, image link and article number
   */
  private final CharSequence[] mShareableRenderedData = new CharSequence[3];
  private PhotoViewAttacher mAttacher;

  private long mStripeNum;
  private Dialog mRateAppDialog;

  @Inject
  NavigationGestureDetector mGestureDetector;
  @Inject
  StripePresenter mStripePresenter;

  @Bind(R.id.content)
  View mContent;
  @Bind(R.id.toolbar)
  CustomTitleToolbar mToolbar;
  @Bind(R.id.navigation)
  NavigationLinearLayout mNavigationLayout;
  @Bind(R.id.progress_bar)
  View mLoading;
  @Bind(R.id.retry)
  RetryLinearLayout mRetry;
  @Bind(R.id.stripe_presenter)
  PhotoViewExceptionProofRelativeLayout mStripePresenterViewGroup;
  @Bind(R.id.image)
  PhotoView mImage;
  @Bind(R.id.title)
  TextView mTitle;
  @Bind(R.id.description)
  TextView mDescription;

  @NonNull
  public static Intent getCallingIntent(final @NonNull Context context, final long stripeNum) {
    final Intent callingIntent = new Intent(context, StripeActivity.class);
    callingIntent.putExtra(INTENT_EXTRA_PARAM_STRIPE_NUM, stripeNum);

    return callingIntent;
  }

  @Override
  public void onCreate(final @Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setupLayout();

    createComponentAndInjectSelf();

    setupToolbar();
    setupNavigationLayout();
    setupLoading();
    initializeActivity(savedInstanceState);
    initializeStripePresenter();
    initializeNavigation();
    initializeRetry();
    initializeImage();
    initializePresenterLayout();
  }

  private void setupLayout() {
    setTheme(ThemeUtil.getAppTheme(this));
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    if (!ThemeUtil.isAppThemeLight(this)) {
        final ColorMatrixColorFilter negative = new ColorMatrixColorFilter(new float[]{
                -1.0f, 0, 0, 0, 255, // red
                0, -1.0f, 0, 0, 255, // green
                0, 0, -1.0f, 0, 255, // blue
                0, 0, 0, 1.0f, 0    // alpha
        });
        ((View) mImage.getParent()).getBackground().setColorFilter(negative);
        mImage.setColorFilter(negative);
        mTitle.getPaint().setColorFilter(negative);
    }
    ((ViewGroup) findViewById(android.R.id.content)).setLayoutTransition(new BoatsLayoutTransition
        (this));
  }

  private void updateLastOpenedEpoch() {
    P.lastOpenedEpoch.put(String.valueOf(System.currentTimeMillis())).apply();
  }

  private void initializeBackgroundTasks() {
    if (GooglePlayUtil.isServicesAvailable(this, false)) {
      BackgroundTaskManager.initialize(GcmNetworkManager.getInstance(this));
    }
  }

  private void updateTaskDescription() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      TypedValue typedValue = new TypedValue();
      getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
      int color = typedValue.data;
      ActivityManager.TaskDescription td
              = new ActivityManager.TaskDescription(
                      getContext().getString(R.string.app_name), null, color);
      setTaskDescription(td);
    }
  }

  private void showRateAppDialog() {
    if (P.ratedGooglePlay.get()) {
      return;
    }
    if (mRateAppDialog == null) {
      //noinspection deprecation -- Yes, deprecated, but the replacement is added in API 22
      mRateAppDialog = new AlertDialog.Builder(this,
          !ThemeUtil.isAppThemeLight(this) ? android.app.AlertDialog.THEME_DEVICE_DEFAULT_DARK
              : android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT).
          setTitle(getString(R.string.rate_popup_title, getString(R.string.app_name)))
          .setMessage(R.string.rate_popup_body)
          .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final @NonNull DialogInterface dialog, final int which) {
              final String packageName;
              final Uri uri = Uri.parse(getString(R.string.market_intent_template,
                  packageName = StripeActivity.this.getPackageName()));
              final Intent intent = new Intent(Intent.ACTION_VIEW, uri);

              intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
              if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //noinspection InlinedApi -- To not to pop lint
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
              } else {
                //noinspection deprecation -- The if statement covers any possible issues
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
              }

              try {
                P.ratedGooglePlay.put(true).apply();
                startActivity(intent);
              } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string
                    .market_link_template, packageName))));
              }
            }
          })
          .setNegativeButton(android.R.string.cancel, null)
          .setCancelable(false)
          .create();
    }
    mRateAppDialog.show();
  }

  private void setupToolbar() {
    setSupportActionBar(mToolbar);
    mToolbar.lockTitle(getString(R.string.app_name));
  }

  private void setupNavigationLayout() {
    final RelativeLayout.LayoutParams navigationLayoutLp =
        (RelativeLayout.LayoutParams) mNavigationLayout.getLayoutParams();
    final boolean isLandscape =
        getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

    if (isLandscape) {
      navigationLayoutLp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
    } else {
      navigationLayoutLp.addRule(RelativeLayout.BELOW, mLoading.getId());
      navigationLayoutLp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
    }

    mNavigationLayout.setLayoutParams(navigationLayoutLp);
  }

  private void setupLoading() {
    final RelativeLayout.LayoutParams progressBarLayoutParams =
        (RelativeLayout.LayoutParams) mLoading.getLayoutParams();

    progressBarLayoutParams.addRule(RelativeLayout.BELOW, R.id.toolbar_wrapper);

    mLoading.setLayoutParams(progressBarLayoutParams);
  }

  @Override
  public boolean onTouchEvent(final @NonNull MotionEvent event) {
    return handleTriplePointerMenuToggle(event) || this.mGestureDetector.onTouchEvent(event)
        || super.onTouchEvent(event);
  }

  private boolean handleTriplePointerMenuToggle(final @NonNull MotionEvent event) {
    if (event.getPointerCount() == 3 && event.getActionMasked() == MotionEvent.ACTION_POINTER_UP) {
      if (mNavigationLayout.isExpanded()) {
        mNavigationLayout.hide();
      } else {
        mNavigationLayout.show();
      }
      return true;
    }
    return false;
  }

  @Override
  public boolean onCreateOptionsMenu(final @NonNull Menu menu) {
    final MenuInflater inflater = getMenuInflater();

    inflater.inflate(R.menu.activity_stripe, menu);

    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onPrepareOptionsMenu(final @NonNull Menu menu) {
    for (int i = 0; i < menu.size(); i++) {
      Drawable icon = menu.getItem(i).getIcon();

      if (icon != null) {
        icon.mutate();
        icon.setColorFilter(ResourceUtil.getAttrColor(this, android.R.attr.textColorPrimary),
            PorterDuff.Mode.SRC_ATOP);
      }
    }
    return super.onPrepareOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(final @NonNull MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_open_in_browser:
        openModelInBrowser();
        return true;
      case R.id.action_share:
        mStripePresenter.actionShare(this);
        return true;
      case R.id.action_first:
        mStripePresenter.switchToStripeNum(DomainStripe.STRIPE_NUM_FIRST);
        return true;
      case R.id.action_last:
        mStripePresenter.switchToStripeNum(DomainStripe.STRIPE_NUM_CURRENT);
        return true;
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

  private void openModelInBrowser() {
    if (!TextUtils.isEmpty(mShareableRenderedData[2])) {
      Intent intent = new Intent(Intent.ACTION_VIEW);
      intent.setData(Uri.parse(
          getContext().getString(R.string.xkcd_mobile_link_pattern, mShareableRenderedData[2])));
      if (intent.resolveActivity(getPackageManager()) != null) {
          startActivity(intent);
      } else {
          Toast.makeText(this, "No browser was found :(", Toast.LENGTH_LONG).show();
      }
    }
  }

  protected void createComponentAndInjectSelf() {
    DaggerStripeComponent.builder()
        .applicationComponent(((MainApplication) getApplication()).getApplicationComponent())
        .stripeModule(new StripeModule(mNavigationLayout, mToolbar, mRetry))
        .build()
        .inject(this);
  }

  @Override
  protected void onSaveInstanceState(final @Nullable Bundle outState) {
    if (outState != null) {
      outState.putLong(INSTANCE_STATE_PARAM_STRIPE_NUM, this.mStripeNum);
    }
    super.onSaveInstanceState(outState);
  }

  @Override
  protected void onRestoreInstanceState(final @Nullable Bundle savedInstanceState) {
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

  @Override
  public void setStripeNum(final long stripeNum) {
    mStripeNum = stripeNum;
  }

  @Override
  public long getStripeNum() {
    return mStripeNum;
  }

  private void initializeStripePresenter() {
    this.mStripePresenter.setBaseView(this);
    this.mStripePresenter.setStripeContentView(this);
    this.mStripePresenter.initialize();
    this.mStripePresenter.switchToStripeNum(mStripeNum);
  }

  private void initializeNavigation() {
    this.mNavigationLayout.setStripePresenter(this.mStripePresenter);
  }

  private void initializeRetry() {
    this.mRetry.setStripePresenter(this.mStripePresenter);
    this.mRetry.setIntermediateGestureDetector(this.mGestureDetector);
  }

  private void initializeImage() {
    this.mAttacher = new PhotoViewAttacher(this.mImage, true, BuildConfig.DEBUG);
    this.mAttacher.setIntermediateGestureDetector(this.mGestureDetector);
  }

  private void initializePresenterLayout() {
    this.mStripePresenterViewGroup.setIntermediateGestureDetector(mGestureDetector);
  }

  @Override
  public void onResume() {
    super.onResume();

    updateLastOpenedEpoch();
    initializeBackgroundTasks();
    updateTaskDescription();

    if (P.shouldRestart.get()) {
      P.scheduledStripeReload.put(true).apply();
      P.shouldRestart.put(false).apply();
      new Handler(Looper.myLooper()).postDelayed(new Runnable() {
        @Override
        public void run() {
          ActivityUtil.restart(StripeActivity.this);
        }
      }, 1);
    } else {
      this.mStripePresenter.resume();
    }
  }

  @Override
  public void onPause() {
    super.onPause();
    this.mStripePresenter.pause();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    this.mStripePresenter.destroy();
  }

  @Override
  public void setTitleTypeface(final @NonNull Typeface titleTypeface) {
    mToolbar.getTitleView().setTypeface(titleTypeface);
  }

  @Override
  public void renderStripe(final @NonNull PresentationStripe model) {
    final CharSequence title;

    mTitle.setText(title = model.getTitle());
    mDescription.setText(model.getAlt());
    Glide.with(this)
        .load(model.getImg())
        .crossFade(getResources().getInteger(R.integer.content_in_duration_milliseconds))
        .into(mImage);
    mImage.setContentDescription(title);
    mAttacher.update();
    this.mAttacher.setMinimumScale(this.mImage.getScale());

    updateShareableData(model);
    saveMaxShownStripe(model.getNum());
    showNavigationTutorial();
    processRateDialogCountUpdate();
  }

  private void saveMaxShownStripe(final long newStripeId) {
    if (newStripeId > P.maxShownStripeNum.get()) {
      P.maxShownStripeNum.put((int) newStripeId).apply();
    }
  }

  private void showNavigationTutorial() {
    final Resources resources;
    final boolean isLandscape = (resources = getResources()).getConfiguration().orientation
        == Configuration.ORIENTATION_LANDSCAPE;

    if (isLandscape) {
      if (!P.tutorialShownLandscape.get()) {
        mNavigationLayout.showTutorial();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
          @Override
          public void run() {
            P.tutorialShownLandscape.put(true).apply();
            mNavigationLayout.hideTutorial();
          }
        }, resources.getInteger(R.integer.tutorial_show_duration_milliseconds));
      }
    } else {
      if (!P.tutorialShownPortrait.get()) {
        mNavigationLayout.showTutorial();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
          @Override
          public void run() {
            P.tutorialShownPortrait.put(true).apply();
            mNavigationLayout.hideTutorial();
          }
        }, resources.getInteger(R.integer.tutorial_show_duration_milliseconds));
      }
    }
  }

  private void processRateDialogCountUpdate() {
    int count = P.remainingStripesUntilReview.get();

    if (count == 0) {
      showRateAppDialog();
      count = getResources().getInteger(R.integer.rate_popup_required_stripes_rendered);
    }

    P.remainingStripesUntilReview.put(count - 1).apply();
  }

  private void updateShareableData(final @NonNull PresentationStripe model) {
    mShareableRenderedData[0] = model.getTitle();
    mShareableRenderedData[1] = model.getImg();
    mShareableRenderedData[2] = String.valueOf(model.getNum());
  }

  @Override
  public void showContent() {
    mStripePresenterViewGroup.setBackgroundColor(
        ResourceUtil.getColor(this, R.color.content_background_normal, getTheme()));
    mStripePresenterViewGroup.setVisibility(View.VISIBLE);
  }

  @Override
  public void hideContent() {
    mStripePresenterViewGroup.setVisibility(View.INVISIBLE);
  }

  @Override
  public void showLoading() {
    if (!mRetry.isShown()) {
      mContent.setBackgroundColor(
          ResourceUtil.getColor(this, R.color.content_background_normal, getTheme()));
    }
    mLoading.setVisibility(View.VISIBLE);
  }

  @Override
  public void hideLoading() {
    mLoading.setVisibility(View.INVISIBLE);
  }

  @Override
  public void showRetry(final @NonNull Throwable throwable) {
    mNavigationLayout.hide();
    mContent.setBackgroundColor(
        ResourceUtil.getColor(this, R.color.content_background_empty, getTheme()));
    mRetry.setVisibility(View.VISIBLE);
  }

  @Override
  public void hideRetry() {
    mRetry.setVisibility(View.INVISIBLE);
  }

  @Override
  public Context getContext() {
    return getApplicationContext();
  }

  @Override
  public void showMessage(@StringRes int messageRes) {
    Toast.makeText(this, messageRes, Toast.LENGTH_SHORT).show();
  }

  @SuppressWarnings("InlinedApi")
  @Override
  public void share() {
    //Prevent trying to share "nothing"
    for (final CharSequence x : mShareableRenderedData) {
      if (TextUtils.isEmpty(x)) {
        return;
      }
    }

    final Intent intent = new Intent(Intent.ACTION_SEND);

    intent.setType("text/plain");
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
    }
    intent.putExtra(Intent.EXTRA_SUBJECT, mShareableRenderedData[0]);
    intent.putExtra(Intent.EXTRA_TEXT, mShareableRenderedData[1]);

    startActivity(Intent.createChooser(intent, getString(R.string.action_share_title)));
  }

  @Override
  public boolean dispatchKeyEvent(final @NonNull KeyEvent event) {
    if (!P.volumeButtonControlNavigationEnabled.get()) {
      return false;
    }

    switch (event.getKeyCode()) {
      case KeyEvent.KEYCODE_VOLUME_UP:
        if (event.getAction() == KeyEvent.ACTION_UP) {
          mStripePresenter.actionPrevious();
          mNavigationLayout.hide();
        }
        return true;
      case KeyEvent.KEYCODE_VOLUME_DOWN:
        if (event.getAction() == KeyEvent.ACTION_UP) {
          mStripePresenter.actionNext();
          mNavigationLayout.hide();
        }
        return true;
      default:
        return super.dispatchKeyEvent(event);
    }
  }

  @Override
  public void onConfigurationChanged(final @NonNull Configuration newConfig) {
    P.scheduledStripeReload.put(true).apply();
    super.onConfigurationChanged(newConfig);
    recreate();
  }
}
