package com.jorge.boats.xkcd.data.preference;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
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

  @Override public Dialog buildDialog(final @NonNull AlertDialog.Builder builder) {
    final Context context = getContext();
    @SuppressLint("InflateParams") final View view =
        ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
            R.layout.preference_about_xkcd, null, false);
    final TextView body;

    (body = (TextView) view.findViewById(R.id.body)).setText(
        Html.fromHtml(context.getString(R.string.pref_message_about_xkcd)));
    body.setMovementMethod(LinkMovementMethod.getInstance());

    return builder.setTitle(R.string.pref_title_about_xkcd)
        .setView(view)
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
