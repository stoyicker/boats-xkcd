package com.jorge.boats.xkcd.view.settings;

import com.jorge.boats.xkcd.R;
import com.jorge.boats.xkcd.util.ResourceUtil;
import com.jorge.boats.xkcd.util.ThemeUtil;
import com.jorge.boats.xkcd.util.UiUtil;
import com.jorge.boats.xkcd.util.ViewServerDelegate;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(ThemeUtil.getSettingsTheme(this));
        setContentView(R.layout.activity_settings);

        initTaskDescription();
        initActionBar();
        initFragment(savedInstanceState);

        ViewServerDelegate.addWindow(this);
    }

    private void initTaskDescription() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) UiUtil.setTaskDescription(this);
    }

    private void initActionBar() {
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            final int color = ResourceUtil.getAttrColor(this, R.attr.background);
            final String htmlColor = String.format(Locale.ENGLISH, "#%06X",
                    (0xFFFFFF & Color.argb(0, Color.red(color), Color.green(color), Color.blue(color))));

            actionBar.setTitle(Html.fromHtml(
                    String.format(Locale.ENGLISH, "<font color=%s>%s</font>", htmlColor,
                            getString(R.string.label_settings))));
            actionBar.setDisplayHomeAsUpEnabled(Boolean.TRUE);
        }
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
