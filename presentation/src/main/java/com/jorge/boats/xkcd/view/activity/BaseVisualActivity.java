package com.jorge.boats.xkcd.view.activity;

import android.support.annotation.LayoutRes;
import butterknife.ButterKnife;
import com.jorge.boats.xkcd.util.ThemeUtil;

public abstract class BaseVisualActivity extends BaseActivity {

  @Override public void setContentView(final @LayoutRes int layoutResID) {
    setTheme(ThemeUtil.getAppTheme(this));
    super.setContentView(layoutResID);
    initButterKnife();
  }

  private void initButterKnife() {
    ButterKnife.bind(this);
  }
}
