package com.jorge.boats.view.activity;

import android.support.v7.app.AppCompatActivity;
import com.jorge.boats.CustomApplication;
import com.jorge.boats.di.component.ApplicationComponent;

public abstract class BaseActivity extends AppCompatActivity {

  protected abstract void createComponentAndInjectSelf();

  protected final ApplicationComponent getApplicationComponent() {
    return ((CustomApplication) getApplication()).getApplicationComponent();
  }
}
