package com.jorge.boats.xkcd.view.activity;

import android.support.annotation.LayoutRes;

import com.jorge.boats.xkcd.MainApplication;
import com.jorge.boats.xkcd.di.component.ApplicationComponent;

public abstract class ButterknifeDaggerActivity extends ButterknifeActivity {

  protected abstract void createComponentAndInjectSelf();

  protected final ApplicationComponent getApplicationComponent() {
    return ((MainApplication) getApplication()).getApplicationComponent();
  }

  @Override
  public void setContentView(final @LayoutRes int layoutResID) {
    super.setContentView(layoutResID);
    createComponentAndInjectSelf();
  }
}
