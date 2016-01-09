package com.jorge.boats.view.activity;

import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
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

  @Override public void setContentView(final int layoutResID) {
    super.setContentView(layoutResID);

    initButterKnife();

    setupToolbar(mToolbar);
    setupNavigator();
  }

  private void initButterKnife() {
    ButterKnife.bind(this);
  }

  private void setupToolbar(final @NonNull Toolbar toolbar) {
    setSupportActionBar(toolbar);
  }

  private void setupNavigator() {
    final RelativeLayout.LayoutParams navigatorLayoutParams =
        (RelativeLayout.LayoutParams) mNavigator.getLayoutParams();
    final boolean isLandscape =
        getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

    if (isLandscape) {
      navigatorLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
      navigatorLayoutParams.addRule(RelativeLayout.BELOW, mToolbar.getId());
    } else {
      navigatorLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
    }

    mNavigator.setLayoutParams(navigatorLayoutParams);
  }

  @NonNull protected CustomTitleToolbar getToolbar() {
    return mToolbar;
  }
}
