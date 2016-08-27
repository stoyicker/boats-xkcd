package com.jorge.boats.xkcd.view.activity;

import android.support.annotation.LayoutRes;

import com.jorge.boats.xkcd.MainApplication;
import com.jorge.boats.xkcd.di.component.ApplicationComponent;

public abstract class ButterKnifeDaggerActivity extends ButterKnifeActivity {

  protected abstract void createComponentAndInjectSelf();

  protected final ApplicationComponent getApplicationComponent() {
    return ((MainApplication) getApplication()).getApplicationComponent();
  }

  @Override
  public void setContentView(final @LayoutRes int layoutResID) {
    super.setContentView(layoutResID);
  }
}
