package com.jorge.boats.xkcd.view.activity;

import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

import com.jorge.boats.xkcd.util.ThemeUtil;

import butterknife.ButterKnife;

public abstract class ButterknifeActivity extends AppCompatActivity {

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
