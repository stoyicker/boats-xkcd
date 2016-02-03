package com.jorge.boats.xkcd.view.settings;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import com.jorge.boats.xkcd.R;
import com.jorge.boats.xkcd.util.UiUtil;

public class SettingsActivity extends AppCompatActivity {

  @Override protected void onCreate(final @Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_settings);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
      UiUtil.setTaskDescription(this);

    final ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setTitle(R.string.label_settings);
      actionBar.setDisplayHomeAsUpEnabled(Boolean.TRUE);
    }
  }

  @Override public boolean onOptionsItemSelected(final @NonNull MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        ActivityCompat.finishAfterTransition(this);
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }
}
