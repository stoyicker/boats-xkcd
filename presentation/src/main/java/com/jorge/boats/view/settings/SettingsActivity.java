package com.jorge.boats.view.settings;

import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class SettingsActivity extends AppCompatActivity {

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
