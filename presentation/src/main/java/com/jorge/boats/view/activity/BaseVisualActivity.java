package com.jorge.boats.view.activity;

import android.support.annotation.LayoutRes;
import butterknife.ButterKnife;

public abstract class BaseVisualActivity extends BaseActivity {

  @Override public void setContentView(final @LayoutRes int layoutResID) {
    super.setContentView(layoutResID);
    initButterKnife();
  }

  private void initButterKnife() {
    ButterKnife.bind(this);
  }
}
