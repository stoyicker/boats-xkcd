package com.jorge.boats.xkcd.view.settings;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.View;

import com.jorge.boats.xkcd.R;
import com.jorge.boats.xkcd.data.P;
import com.jorge.boats.xkcd.data.preference.CustomDialogPreference;
import com.jorge.boats.xkcd.data.preference.list.StyledListPreference;
import com.jorge.boats.xkcd.util.ActivityUtil;
import com.jorge.boats.xkcd.util.ResourceUtil;
import com.jorge.boats.xkcd.util.ThemeUtil;

import rx.Subscription;
import rx.functions.Action1;

public class SettingsPreferenceFragmentCompat extends PreferenceFragmentCompat {

  public static final String FRAGMENT_TAG =
      SettingsPreferenceFragmentCompat.class.getCanonicalName();

  private Subscription mVolumeKeyNavigationSummary, mSwipeNavigationSummary;
  private StyledListPreference.ThemeChangeListener mThemeChangeListener;

  @Override
  public void onCreatePreferences(final @Nullable Bundle bundle, final String rootKey) {
    setPreferencesFromResource(com.jorge.boats.xkcd.data.R.xml.prefs_settings, rootKey);

    initializeVolumeKeyControlPreference();
    initializeSwipeBrowsePreference();
    initializeThemeChangeListener();
    initializeSharePreference();
  }

    @Override
    public void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            TypedValue typedValue = new TypedValue();
            getActivity().getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
            int color = typedValue.data;
            ActivityManager.TaskDescription td
                    = new ActivityManager.TaskDescription(
                    getContext().getString(R.string.app_name), null, color);
            getActivity().setTaskDescription(td);
        }
    }

    private void initializeVolumeKeyControlPreference() {

    mVolumeKeyNavigationSummary = P.swipeControlNavigationEnabled.rx()
        .asObservable()
        .subscribe(new SwipeControlChangeAction(
            findPreference(P.swipeControlNavigationEnabled.key)));
  }

  private void initializeSwipeBrowsePreference() {

    mSwipeNavigationSummary = P.volumeButtonControlNavigationEnabled.rx()
        .asObservable()
        .subscribe(new VolumeButtonControlChangeAction(
            findPreference(P.volumeButtonControlNavigationEnabled.key)));
  }

  private void initializeThemeChangeListener() {
    mThemeChangeListener = new StyledListPreference.ThemeChangeListener() {
      @Override
      public void onThemeChanged() {
          P.shouldRestart.put(true).apply();
        ActivityUtil.restart(getActivity());
      }
    };
  }

  private void initializeSharePreference() {
    findPreference(P.share.key).setOnPreferenceClickListener(
        new Preference.OnPreferenceClickListener() {

          @SuppressWarnings("InlinedApi")
          @Override
          public boolean onPreferenceClick(final @NonNull Preference preference) {
            final Intent intent = new Intent(Intent.ACTION_SEND);
            final Context context;

            intent.setType("text/plain");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
              intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
            }
            intent.putExtra(Intent.EXTRA_TEXT,
                (context = getContext()).getString(R.string.market_base_url,
                    context.getApplicationInfo().packageName));

            startActivity(Intent.createChooser(intent, getString(R.string.action_share_title)));

            return true;
          }
        });
  }

  @Override
  public void onDetach() {
    if (!mVolumeKeyNavigationSummary.isUnsubscribed()) {
      mVolumeKeyNavigationSummary.unsubscribe();
    }
    if (!mSwipeNavigationSummary.isUnsubscribed()) {
      mSwipeNavigationSummary.unsubscribe();
    }

    super.onDetach();
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    //Avoid transparency
    view.setBackgroundColor(ResourceUtil.getAttrColor(getContext(), R.attr.background));
  }

  @Override
  public void onDisplayPreferenceDialog(final @NonNull Preference preference) {
    final Context context;
    final boolean isDark;

    if (preference instanceof CustomDialogPreference) {
      ((CustomDialogPreference) preference).buildDialog(new AlertDialog.Builder(
          new ContextThemeWrapper(context = getContext(),
              (isDark = ThemeUtil.isSettingsThemeDark(context)) ? R.style.DarkDialog
                  : R.style.LightDialog)), isDark).show();
    } else if (preference instanceof StyledListPreference) {
      ((StyledListPreference) preference).buildDialog(new AlertDialog.Builder(
          new ContextThemeWrapper(context = getContext(),
              (isDark = ThemeUtil.isSettingsThemeDark(context)) ? R.style.DarkDialog
                  : R.style.LightDialog)), isDark, mThemeChangeListener).show();
    }
  }

  private static class VolumeButtonControlChangeAction implements Action1<Boolean> {

    private final Preference mObservedPreference;

    private VolumeButtonControlChangeAction(final @Nullable Preference observedPreference) {
      mObservedPreference = observedPreference;
    }

    @Override
    public void call(final @NonNull Boolean value) {
      if (mObservedPreference != null) {
        mObservedPreference.setSummary(value ? R.string.pref_summary_volume_navigation_on
            : R.string.pref_summary_volume_navigation_off);
      }
    }
  }

  private static class SwipeControlChangeAction implements Action1<Boolean> {

    private final Preference mObservedPreference;

    private SwipeControlChangeAction(final @Nullable Preference observedPreference) {
      mObservedPreference = observedPreference;
    }

    @Override
    public void call(final @NonNull Boolean value) {
      if (mObservedPreference != null) {
        mObservedPreference.setSummary(value ? R.string.pref_summary_swipe_navigation_on
            : R.string.pref_summary_swipe_navigation_off);
      }
    }
  }
}
