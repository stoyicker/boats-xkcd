package com.jorge.boats.xkcd.view.activity;

import android.support.annotation.LayoutRes;

import com.jorge.boats.xkcd.util.ThemeUtil;

import butterknife.ButterKnife;

public abstract class ButterknifeActivity extends ViewServerActivity {

  @Override
  public void setContentView(final @LayoutRes int layoutResID) {
    setTheme(ThemeUtil.getAppTheme(this));
    super.setContentView(layoutResID);
    initButterKnife();
  }

  private void initButterKnife() {
    ButterKnife.bind(this);
  }
}
