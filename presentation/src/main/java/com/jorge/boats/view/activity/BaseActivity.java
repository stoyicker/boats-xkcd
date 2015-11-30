package com.jorge.boats.view.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.jorge.boats.CustomApplication;
import com.jorge.boats.di.component.ApplicationComponent;
import com.jorge.boats.di.module.ActivityModule;
import com.jorge.boats.navigation.Navigator;

import javax.inject.Inject;

public abstract class BaseActivity extends Activity {

  @Inject Navigator mNavigator;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.getApplicationComponent().inject(this);
  }

  void addFragment(int containerViewId, Fragment fragment) {
    final FragmentTransaction fragmentTransaction = this.getFragmentManager().beginTransaction();
    fragmentTransaction.add(containerViewId, fragment);
    fragmentTransaction.commit();
  }

  private ApplicationComponent getApplicationComponent() {
    return ((CustomApplication) getApplication()).getApplicationComponent();
  }

  ActivityModule getActivityModule() {
    return new ActivityModule(this);
  }
}
