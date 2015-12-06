package com.jorge.boats.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.jorge.boats.CustomApplication;
import com.jorge.boats.di.component.ApplicationComponent;
import com.jorge.boats.navigation.Navigator;
import javax.inject.Inject;

public abstract class BaseActivity extends AppCompatActivity {

  @Inject Navigator mNavigator;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.getApplicationComponent().inject(this);
  }

  protected ApplicationComponent getApplicationComponent() {
    return ((CustomApplication) getApplication()).getApplicationComponent();
  }
}
