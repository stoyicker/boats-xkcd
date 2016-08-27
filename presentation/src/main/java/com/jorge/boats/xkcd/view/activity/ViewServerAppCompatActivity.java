package com.jorge.boats.xkcd.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.jorge.boats.xkcd.util.ViewServerDelegate;

public abstract class ViewServerAppCompatActivity extends AppCompatActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    ViewServerDelegate.addWindow(this);
  }

  @Override
  protected void onResume() {
    super.onResume();

    ViewServerDelegate.setFocusedWindow(this);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();

    ViewServerDelegate.removeWindow(this);
  }
}
