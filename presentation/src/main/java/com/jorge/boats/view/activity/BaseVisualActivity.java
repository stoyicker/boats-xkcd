package com.jorge.boats.view.activity;

import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.RelativeLayout;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.jorge.boats.R;
import com.jorge.boats.view.widget.CustomTitleToolbar;

public abstract class BaseVisualActivity extends BaseActivity {

  @Bind(android.R.id.content) View mRoot;

  @Bind(R.id.toolbar) CustomTitleToolbar mToolbar;

  @Bind(R.id.navigator) View mNavigator;

  @Bind(R.id.progress_bar) View mProgressBar;

  @Override public void setContentView(final int layoutResID) {
    super.setContentView(layoutResID);

    initButterKnife();

    setupToolbar();
    setupNavigator();
    setupProgressBar();
  }

  private void initButterKnife() {
    ButterKnife.bind(this);
  }

  private void setupToolbar() {
    setSupportActionBar(mToolbar);
  }

  private void setupNavigator() {
    final RelativeLayout.LayoutParams navigatorLayoutParams =
        (RelativeLayout.LayoutParams) mNavigator.getLayoutParams();
    final boolean isLandscape =
        getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

    if (isLandscape) {
      navigatorLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
      navigatorLayoutParams.addRule(RelativeLayout.BELOW, mProgressBar.getId());
    } else {
      navigatorLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
    }

    mNavigator.setLayoutParams(navigatorLayoutParams);
  }

  private void setupProgressBar() {
    final RelativeLayout.LayoutParams progressBarLayoutParams =
        (RelativeLayout.LayoutParams) mProgressBar.getLayoutParams();

    progressBarLayoutParams.addRule(RelativeLayout.BELOW, R.id.toolbar_wrapper);

    mProgressBar.setLayoutParams(progressBarLayoutParams);
  }

  @NonNull protected CustomTitleToolbar getToolbar() {
    return mToolbar;
  }

  @NonNull protected View getLoadingView() {
    return mProgressBar;
  }
}
