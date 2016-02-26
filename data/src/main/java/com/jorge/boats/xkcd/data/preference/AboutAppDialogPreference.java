package com.jorge.boats.xkcd.data.preference;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import com.jorge.boats.xkcd.data.R;

public class AboutAppDialogPreference extends CustomDialogPreference {

  public AboutAppDialogPreference(final @NonNull Context context) {
    super(context);
  }

  public AboutAppDialogPreference(final @NonNull Context context,
      final @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public AboutAppDialogPreference(final @NonNull Context context,
      final @Nullable AttributeSet attrs, final int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public AboutAppDialogPreference(final @NonNull Context context,
      final @Nullable AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  @Override public Dialog buildDialog() {
    final Context context = getContext();
    PackageInfo packageInfo = null;
    try {
      packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
    } catch (final PackageManager.NameNotFoundException ignored) {
    }

    final AlertDialog.Builder ret =
        new AlertDialog.Builder(context).setTitle(R.string.pref_title_about_xkcd);

    if (packageInfo != null) {
      ret.setMessage(context.getString(R.string.pref_message_about_app, packageInfo.versionName,
          packageInfo.versionCode));
    }
    ret.setNegativeButton(android.R.string.cancel, null)
        .setPositiveButton(R.string.licenses, new DialogInterface.OnClickListener() {
          @Override public void onClick(final @NonNull DialogInterface dialog, final int which) {
            final String url = context.getString(R.string.licenses_url);
            final Intent i = new Intent(Intent.ACTION_VIEW);

            i.setData(Uri.parse(url));
            context.startActivity(i);
          }
        });

    return ret.create();
  }
}
