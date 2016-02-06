package com.jorge.boats.xkcd.view.settings;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.view.View;
import com.jorge.boats.xkcd.R;

public class SettingsPreferenceFragment extends PreferenceFragment {

  @Override public void onCreate(final @Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    addPreferencesFromResource(R.xml.prefs_user_editable);
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
  }
}
