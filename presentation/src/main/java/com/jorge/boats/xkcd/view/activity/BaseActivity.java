package com.jorge.boats.xkcd.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.jorge.boats.xkcd.CustomApplication;
import com.jorge.boats.xkcd.di.component.ApplicationComponent;

public abstract class BaseActivity extends AppCompatActivity {

  @Override protected void onCreate(final @Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  protected abstract void createComponentAndInjectSelf();

  protected final ApplicationComponent getApplicationComponent() {
    return ((CustomApplication) getApplication()).getApplicationComponent();
  }
}
