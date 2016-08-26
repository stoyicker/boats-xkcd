package com.jorge.boats.xkcd.view.settings;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.jorge.boats.xkcd.R;
import com.jorge.boats.xkcd.util.ResourceUtil;
import com.jorge.boats.xkcd.util.ThemeUtil;
import com.jorge.boats.xkcd.view.activity.ButterknifeActivity;
import com.jorge.boats.xkcd.view.widget.CustomTitleToolbar;

import butterknife.Bind;

public class SettingsActivity extends ButterknifeActivity {

  @Bind(R.id.toolbar)
  CustomTitleToolbar mToolbar;

  @Override
  protected void onCreate(final @Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_settings);

    setupToolbar();
    initFragment(savedInstanceState);
    setTheme(ThemeUtil.getSettingsTheme(this));
  }

  private void setupToolbar() {
    mToolbar.setTitle(R.string.label_settings);
    setSupportActionBar(mToolbar);

    final ActionBar actionBar = getSupportActionBar();
    //noinspection ConstantConditions
    actionBar.setDisplayHomeAsUpEnabled(Boolean.TRUE);
    final Drawable upArrow = ResourceUtil.getDrawable(this, R.drawable.ic_arrow_left, getTheme());
    //noinspection ConstantConditions
    upArrow.setColorFilter(ResourceUtil.getAttrColor(this, android.R.attr.textColorPrimary),
        PorterDuff.Mode.SRC_ATOP);
    actionBar.setHomeAsUpIndicator(upArrow);
  }

  @Override
  public boolean onCreateOptionsMenu(final @NonNull Menu menu) {
    final MenuInflater inflater = getMenuInflater();

    inflater.inflate(R.menu.activity_settings, menu);

    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onPrepareOptionsMenu(final @NonNull Menu menu) {
    if (menu.size() > 0) {
      final Drawable showLinkedIn = menu.getItem(0).getIcon();

      if (showLinkedIn != null) {
        showLinkedIn.mutate();
        showLinkedIn.setColorFilter(ResourceUtil.getAttrColor(this, R.attr.background),
            PorterDuff.Mode.SRC_ATOP);
      }
    }
    return super.onPrepareOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(final @NonNull MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        super.onBackPressed();
        return true;
      case R.id.action_linkedin:
        openLinkedInProfile();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  private void openLinkedInProfile() {
    startActivity(new Intent(Intent.ACTION_VIEW,
        Uri.parse("http://www.linkedin.com/profile/view?id=jorgediazbenitosoriano")));
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
