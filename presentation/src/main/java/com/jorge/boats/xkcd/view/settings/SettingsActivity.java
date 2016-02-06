package com.jorge.boats.xkcd.view.settings;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import com.jorge.boats.xkcd.R;
import com.jorge.boats.xkcd.util.UiUtil;

public class SettingsActivity extends AppCompatActivity {

  @Override protected void onCreate(final @Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_settings);

    initTaskDescription();
    initActionBar();
    initFragment(savedInstanceState);
  }

  private void initTaskDescription() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) UiUtil.setTaskDescription(this);
  }

  private void initActionBar() {
    final ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setTitle(R.string.label_settings);
      actionBar.setDisplayHomeAsUpEnabled(Boolean.TRUE);
    }
  }

  @Override public boolean onOptionsItemSelected(final @NonNull MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        NavUtils.navigateUpFromSameTask(this);
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  private void initFragment(final @Nullable Bundle savedInstanceState) {
    if (savedInstanceState == null) {
      final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
      Fragment fragment = getSupportFragmentManager().findFragmentByTag(
          SettingsPreferenceFragmentCompat.FRAGMENT_TAG);

      if (fragment == null) {
        fragment = new SettingsPreferenceFragmentCompat();
      }

      ft.replace(R.id.fragment_container, fragment, SettingsPreferenceFragmentCompat.FRAGMENT_TAG);
      ft.commit();
    }
  }
}
