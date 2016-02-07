package com.jorge.boats.xkcd.view.settings;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.View;
import com.jorge.boats.xkcd.R;
import com.jorge.boats.xkcd.data.P;
import com.jorge.boats.xkcd.util.ProductUtil;
import com.jorge.boats.xkcd.util.ResourceUtil;
import rx.Subscription;
import rx.functions.Action1;

public class SettingsPreferenceFragmentCompat extends PreferenceFragmentCompat {

  public static final String FRAGMENT_TAG =
      SettingsPreferenceFragmentCompat.class.getCanonicalName();

  private Subscription mVolumeKeyNavigationSummary, mThemeSwitch;

  @Override public void onCreatePreferences(final @Nullable Bundle bundle, final String rootKey) {
    setPreferencesFromResource(com.jorge.boats.xkcd.data.R.xml.prefs_user_editable, rootKey);

    initializeVolumeKeyControlPreference();
    initializeThemePreference();
  }

  private void initializeVolumeKeyControlPreference() {
    final Preference volumeControlPreference;

    mVolumeKeyNavigationSummary = P.volumeButtonControlNavigationEnabled.rx()
        .asObservable()
        .subscribe(new VolumeButtonControlChangeAction(
            volumeControlPreference = findPreference(P.volumeButtonControlNavigationEnabled.key)));
    volumeControlPreference.setOnPreferenceChangeListener(
        new Preference.OnPreferenceChangeListener() {
          @Override public boolean onPreferenceChange(final @NonNull Preference preference,
              final @NonNull Object o) {
            if (ProductUtil.hasProPower()) {
              return true;
            } else {
              ProductUtil.showProAppPlayStoreEntry(
                  SettingsPreferenceFragmentCompat.this.getContext());
              return false;
            }
          }
        });
  }

  private void initializeThemePreference() {
    final Preference themePreference;

    mThemeSwitch = P.themeName.rx()
        .asObservable()
        .subscribe(new ThemeChangeAction(themePreference = findPreference(P.themeName.key)));
    themePreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
      @Override public boolean onPreferenceChange(final @NonNull Preference preference,
          final @NonNull Object o) {
        if (ProductUtil.hasProPower()) {
          return true;
        } else {
          ProductUtil.showProAppPlayStoreEntry(SettingsPreferenceFragmentCompat.this.getContext());
          return false;
        }
      }
    });
  }

  @Override public void onDetach() {
    if (!mVolumeKeyNavigationSummary.isUnsubscribed()) {
      mVolumeKeyNavigationSummary.unsubscribe();
    }
    if (!mThemeSwitch.isUnsubscribed()) mThemeSwitch.unsubscribe();

    super.onDetach();
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    //Avoid transparency
    view.setBackgroundColor(ResourceUtil.getAttrColor(getContext(), R.attr.background));
  }

  private static class VolumeButtonControlChangeAction implements Action1<Boolean> {

    private final Preference mObservedPreference;

    private VolumeButtonControlChangeAction(final @Nullable Preference observedPreference) {
      mObservedPreference = observedPreference;
    }

    @Override public void call(final @NonNull Boolean value) {
      if (mObservedPreference != null) {
        mObservedPreference.setSummary(value ? R.string.pref_summary_volume_navigation_on
            : R.string.pref_summary_volume_navigation_off);
      }
    }
  }

  private static class ThemeChangeAction implements Action1<String> {

    private final Preference mObservedPreference;

    private ThemeChangeAction(final @Nullable Preference observedPreference) {
      mObservedPreference = observedPreference;
    }

    @Override public void call(final @NonNull String s) {
      if (mObservedPreference != null) mObservedPreference.setSummary(s);
    }
  }
}
