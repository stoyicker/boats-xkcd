package com.jorge.boats.xkcd.data.preference;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import com.jorge.boats.xkcd.data.R;

public class AboutXkcdDialogPreference extends CustomDialogPreference {

  public AboutXkcdDialogPreference(final @NonNull Context context) {
    super(context);
  }

  public AboutXkcdDialogPreference(final @NonNull Context context,
      final @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public AboutXkcdDialogPreference(final @NonNull Context context,
      final @Nullable AttributeSet attrs, final int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public AboutXkcdDialogPreference(final @NonNull Context context,
      final @Nullable AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  @Override public Dialog buildDialog() {
    final Context context;

    return new AlertDialog.Builder(context = getContext()).setTitle(
        com.jorge.boats.xkcd.data.R.string.pref_title_about_xkcd)
        .setMessage(context.getString(com.jorge.boats.xkcd.data.R.string.pref_message_about_xkcd))
        .setNegativeButton(android.R.string.cancel, null)
        .setPositiveButton(R.string.read_more, new DialogInterface.OnClickListener() {
          @Override public void onClick(final @NonNull DialogInterface dialog, final int which) {
            final String url = context.getString(R.string.xkcd_base_url);
            final Intent i = new Intent(Intent.ACTION_VIEW);

            i.setData(Uri.parse(url));
            context.startActivity(i);
          }
        })
        .create();
  }
}
